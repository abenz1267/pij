package entities.media;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import com.google.inject.Singleton;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import entities.AbstractEntityService;
import entities.location.Location;
import entities.location.LocationService;
import entities.person.Person;
import entities.person.PersonService;
import entities.personmedia.PersonMedia;
import entities.personmedia.PersonMediaService;
import entities.resolution.ResolutionService;
import entities.tag.Tag;
import entities.tag.TagService;
import entities.tagmedia.TagMedia;
import entities.tagmedia.TagMediaService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;
import resources.ResourceService;

/** Implementation for {@link MediaService} */
@Singleton
public class MediaServiceImpl extends AbstractEntityService implements MediaService {
  @Inject private LocationService locationService;
  @Inject private ResolutionService resolutionService;
  @Inject private ResourceService resourceService;
  @Inject private PersonService personService;
  @Inject private TagService tagService;
  @Inject private TagMediaService tagMediaService;
  @Inject private PersonMediaService personMediaService;

  private Dao<Media, Integer> iDao = null;
  private boolean keepOriginal = true;

  public Dao<Media, Integer> dao() {
    if (this.iDao == null) {
      try {
        this.iDao = DaoManager.createDao(this.databaseConnectionService.get(), Media.class);
      } catch (SQLException e) {
        logger.log(Level.SEVERE, e.getMessage());
      }
    }

    return this.iDao;
  }

  public void importMedia(List<File> files) throws IOException, SQLException {
    for (var file : files) {
      var media = new Media(file, resourceService.getMediaDir());

      this.create(media);

      var copied = new File(resourceService.getMediaDir(), file.getName());
      com.google.common.io.Files.copy(file, copied);

      if (!this.keepOriginal) {
        FileUtils.delete(file);
      }
    }
  }

  public void importMediaFromExport(File file) throws IOException, SQLException {
    var tmpDir = new File(resourceService.getMediaDir(), "tmp");
    tmpDir.mkdir();

    this.unzip(tmpDir, file);

    var mapper = new ObjectMapper();
    var json = Paths.get(tmpDir.toString(), file.getName().replace(".zip", ""), "info.json");
    List<Media> media = mapper.readValue(json.toFile(), new TypeReference<List<Media>>() {});

    try {
      this.createMultiple(media);
    } catch (SQLException e) {
      FileUtils.deleteDirectory(tmpDir);
      throw new SQLException(e);
    }

    for (var m : media) {
      var src = Paths.get(tmpDir.toString(), file.getName().replace(".zip", ""), m.getFilename());
      try {
        Files.copy(src.toFile(), new File(m.getFilename()));
      } catch (IOException e) {
        FileUtils.deleteDirectory(tmpDir);
        throw new IOException(e);
      }
    }

    if (!this.keepOriginal) {
      FileUtils.delete(file);
    }

    FileUtils.deleteDirectory(tmpDir);
  }

  /**
   * Extracts a given zip file into a given temporary directory;
   *
   * @param tmpDir the directory
   * @param zip the zip-file
   * @throws IOException
   * @author Andrej Benz
   */
  private void unzip(File tmpDir, File zip) throws IOException {
    var buffer = new byte[1024];

    try (var zis = new ZipInputStream(new FileInputStream(zip.toString()))) {
      ZipEntry zipEntry;
      while ((zipEntry = zis.getNextEntry()) != null) {
        var newFile = newFile(tmpDir, zipEntry);

        if (zipEntry.isDirectory()) {
          if (!newFile.isDirectory() && !newFile.mkdirs()) {
            throw new IOException("Failed to create directory " + newFile);
          }

          continue;
        }

        var parent = newFile.getParentFile();
        if (!parent.isDirectory() && !parent.mkdirs()) {
          throw new IOException("Failed to create directory " + parent);
        }

        try (var fos = new FileOutputStream(newFile)) {
          int len;
          while ((len = zis.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
          }
        }
      }

      zis.closeEntry();
    }
  }

  /**
   * Creates a new file in the destination dir for a zip entry.
   *
   * @param destinationDir the destinationDir.
   * @param zipEntry the {@link ZipEntry}
   * @throws IOException
   * @author Andrej Benz
   */
  private File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
    var destFile = new File(destinationDir, zipEntry.getName());

    String destDirPath = destinationDir.getCanonicalPath();
    String destFilePath = destFile.getCanonicalPath();

    if (!destFilePath.startsWith(destDirPath + File.separator)) {
      throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
    }

    return destFile;
  }

  public void exportMedia(List<Media> media, Path output) throws SQLException, IOException {
    var tmpDir = new File(resourceService.getMediaDir(), "tmp");
    var exportMediaFolder = new File(tmpDir, resourceService.getMediaDir());
    tmpDir.mkdir();
    exportMediaFolder.mkdir();

    for (var m : media) {
      this.dao().refresh(m);
      resolutionService.dao().refresh(m.getResolution());

      var orig = new File(m.getFilename());
      var copied = new File(exportMediaFolder, orig.getName());
      Files.copy(orig, copied);
    }

    var mapper = new ObjectMapper();
    mapper.writeValue(new File(tmpDir, "info.json"), media);

    var fos = new FileOutputStream(output.toFile());
    var zipOut = new ZipOutputStream(fos);

    this.zipFolder(tmpDir, output.getFileName().toString().replace(".zip", ""), zipOut);

    zipOut.close();
    fos.close();
    FileUtils.deleteDirectory(tmpDir);
  }

  /**
   * Creats zip from a folder and saves it to the destination.
   *
   * @param folderToZip the folder to be zipped
   * @param outputFile the outputs filename
   * @param zipOut the {@link ZipOutputStream}
   * @author Andrej Benz
   */
  private void zipFolder(File folderToZip, String outputFile, ZipOutputStream zipOut)
      throws IOException {
    if (folderToZip.isHidden()) {
      return;
    }
    if (folderToZip.isDirectory()) {
      if (outputFile.endsWith("/")) {
        zipOut.putNextEntry(new ZipEntry(outputFile));
        zipOut.closeEntry();
      } else {
        zipOut.putNextEntry(new ZipEntry(outputFile + "/"));
        zipOut.closeEntry();
      }
      File[] children = folderToZip.listFiles();
      for (File childFile : children) {
        zipFolder(childFile, outputFile + "/" + childFile.getName(), zipOut);
      }
      return;
    }

    var zipEntry = new ZipEntry(outputFile);
    zipOut.putNextEntry(zipEntry);
    var bytes = new byte[1024];
    int length;

    try (var fis = new FileInputStream(folderToZip)) {
      while ((length = fis.read(bytes)) >= 0) {
        zipOut.write(bytes, 0, length);
      }
    }
  }

  /** Used to make sure all or none of the files get imported. */
  private void createMultiple(List<Media> media) throws SQLException {
    this.transaction(
        () -> {
          for (var m : media) {
            locationService.checkLocation(m);
            resolutionService.checkResolution(m);

            this.dao().createIfNotExists(m);
          }

          return null;
        });
  }

  public void create(Media media) throws SQLException {
    this.transaction(
        () -> {
          locationService.checkLocation(media);
          resolutionService.checkResolution(media);

          this.dao().createIfNotExists(media);

          return null;
        });
  }

  public void setKeepOriginal(boolean val) {
    this.keepOriginal = val;
  }

  public List<Media> filterMediaByInput(String input) throws SQLException {
    var dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    Date date = null;
    try {
      var localDate = LocalDate.from(dateTimeFormatter.parse(input));
      date = Date.from(localDate.atStartOfDay().toInstant(ZoneOffset.UTC));
    } catch (DateTimeParseException e) {
      this.logger.log(Level.INFO, e.getMessage());
    }

    input = "%" + input + "%";

    QueryBuilder<Media, Integer> mediaQB = dao().queryBuilder();
    QueryBuilder<Location, Integer> locationQB = locationService.dao().queryBuilder();
    QueryBuilder<Person, Integer> personQB = personService.dao().queryBuilder();
    QueryBuilder<PersonMedia, Integer> personMediaQB = personMediaService.dao().queryBuilder();
    QueryBuilder<Tag, Integer> tagQB = tagService.dao().queryBuilder();
    QueryBuilder<TagMedia, Integer> tagMediaQB = tagMediaService.dao().queryBuilder();

    locationQB.where().like("name", input);
    personQB.where().like("firstname", input).or().like("lastname", input);
    tagQB.where().like("name", input);

    personMediaQB.leftJoinOr(personQB);
    tagMediaQB.leftJoinOr(tagQB);

    mediaQB.leftJoinOr(locationQB).leftJoinOr(personMediaQB).leftJoinOr(tagMediaQB);
    Where<Media, Integer> whereMedia = mediaQB.where();

    whereMedia.like("filename", input).or().like("name", input).or().like("description", input);
    if (date != null) whereMedia.or().eq("datetime", date);

    PreparedQuery<Media> preparedQuery = mediaQB.prepare();
    return dao().query(preparedQuery);
  }

  public void refreshAll(Media media) {
    try {
      dao().refresh(media);
      resolutionService.dao().refresh(media.getResolution());
      locationService.dao().refresh(media.getLocation());

      media.setPersons(personMediaService.getPersons(media));
      media.setTags(tagMediaService.getTags(media));

    } catch (SQLException e) {
      this.logger.log(Level.SEVERE, e.getMessage());
    }
  }

  public void delete(Media media) throws SQLException, IOException {
    if (media.getLocation() != null) {
      List<Media> locationRes = dao().queryForEq("location_id", media.getLocation().getId());
      if (locationRes.size() == 1) {
        locationService.dao().delete(media.getLocation());
      }
    }

    List<Media> resolutionRes = dao().queryForEq("resolution_id", media.getResolution().getId());
    if (resolutionRes.size() == 1) {
      resolutionService.dao().delete(media.getResolution());
    }

    var file = new File(media.getFilename());
    FileUtils.delete(file);
    dao().delete(media);
  }

  public void update(Media media) throws SQLException {
    this.checkPersons(media);
    this.checkTags(media);
    locationService.checkLocation(media);
    this.dao().update(media);
    media.setPersons(new ArrayList<>());
    media.setTags(new ArrayList<>());
  }

  private void checkPersons(Media media) throws SQLException {
    var persons = media.getPersons();

    for (var i = 0; i < persons.size(); i++) {
      var person = persons.get(i);
      if ((person.getFirstname().isEmpty() || person.getLastname().isEmpty())
          || person.getId() != 0) {
        continue;
      }

      var b = personService.dao().queryBuilder();
      b.where().eq("firstname", person.getFirstname()).and().eq("lastname", person.getLastname());

      PreparedQuery<Person> preparedQuery = b.prepare();
      List<Person> personList = personService.dao().query(preparedQuery);
      var personMedia = new PersonMedia(persons.get(i), media);

      if (!personList.isEmpty()) {
        persons.get(i).setId(personList.get(0).getId());

        List<PersonMedia> res = personMediaService.get(personList.get(0), media);

        if (res.isEmpty()) {

          personMediaService.dao().createIfNotExists(personMedia);
        }
      } else {
        personService.dao().create(persons.get(i));
        personMediaService.dao().create(personMedia);
      }
    }
  }

  private void checkTags(Media media) throws SQLException {
    var tags = media.getTags();

    for (var i = 0; i < tags.size(); i++) {
      var tag = tags.get(i);
      if (tag.getName().isEmpty() || tag.getId() != 0) {
        continue;
      }

      List<Tag> tagList = tagService.dao().queryForEq("name", tag.getName());
      var tagMedia = new TagMedia(tags.get(i), media);

      if (!tagList.isEmpty()) {
        tags.get(i).setId(tagList.get(0).getId());

        List<TagMedia> res = tagMediaService.get(tagList.get(0), media);

        if (res.isEmpty()) {
          tagMediaService.dao().createIfNotExists(tagMedia);
        }
      } else {
        tagService.dao().create(tags.get(i));
        tagMediaService.dao().create(tagMedia);
      }
    }
  }
}
