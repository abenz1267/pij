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
import entities.location.LocationServiceImpl;
import entities.person.Person;
import entities.person.PersonService;
import entities.resolution.Resolution;
import entities.resolution.ResolutionService;
import entities.tag.Tag;
import entities.tag.TagService;
import org.apache.commons.io.FileUtils;
import resources.ResourceService;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Singleton
public class MediaServiceImpl extends AbstractEntityService implements MediaService {
    @Inject
    private LocationService locationService;
    @Inject
    private ResolutionService resolutionService;
    @Inject
    private ResourceService resourceService;
    @Inject
    private PersonService personService;
    @Inject
    private TagService tagService;

    private Dao<Media, Integer> dao = null;
    private boolean keepOriginal = true;

    public Dao<Media, Integer> dao() {
        if (this.dao == null) {
            try {
                this.dao = DaoManager.createDao(this.databaseConnectionService.get(), Media.class);
            } catch (SQLException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }

        return this.dao;
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
        List<Media> media = mapper.readValue(json.toFile(), new TypeReference<List<Media>>() {
        });

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

    private void zipFolder(File fileToZip, String fileName, ZipOutputStream zipOut)
            throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFolder(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }

        var zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        var bytes = new byte[1024];
        int length;

        try (var fis = new FileInputStream(fileToZip)) {
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
        }
    }

    private void createMultiple(List<Media> media) throws SQLException {
        this.transaction(
                () -> {
                    for (var m : media) {
                        this.checkLocation(m);
                        this.checkResolution(m);

                        this.dao().createIfNotExists(m);
                    }

                    return null;
                });
    }

    private void create(Media media) throws SQLException {
        this.transaction(
                () -> {
                    this.checkLocation(media);
                    this.checkResolution(media);

                    this.dao().createIfNotExists(media);

                    return null;
                });
    }

    private void checkLocation(Media media) throws SQLException {
        if (media.getLocation() != null) {
            List<Location> res =
                    this.locationService.dao().queryForEq("name", media.getLocation().getName());

            if (!res.isEmpty()) {
                media.setLocation(res.get(0));
            } else {
                this.locationService.dao().createIfNotExists(media.getLocation());
            }
        }
    }

    private void checkResolution(Media media) throws SQLException {
        Map<String, Object> kv = new HashMap<>();
        kv.put("height", media.getResolution().getHeight());
        kv.put("width", media.getResolution().getWidth());

        List<Resolution> res = this.resolutionService.dao().queryForFieldValuesArgs(kv);

        if (!res.isEmpty()) {
            media.setResolution(res.get(0));
        } else {
            this.resolutionService.dao().createIfNotExists(media.getResolution());
        }
    }

    public void setKeepOriginal(boolean val) {
        this.keepOriginal = val;
    }

    public List<Media> filterMediaByInput(String input) throws SQLException {
        var dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(input);
        } catch (ParseException e) {
            //ignore;
        }
        input = "%" + input + "%";

        QueryBuilder<Media, Integer> mediaQB = dao.queryBuilder();
        QueryBuilder<Location, Integer> locationQB = locationService.dao().queryBuilder();
        QueryBuilder<Person, Integer> personQB = personService.dao().queryBuilder();
        QueryBuilder<Tag, Integer> tagQB = tagService.dao().queryBuilder();

        locationQB.where().like("name", input);
        personQB.where().like("firstname", input).or().like("lastname", input);
        tagQB.where().like("name", input);

        mediaQB.leftJoinOr(locationQB);//.leftJoinOr(personQB).leftJoinOr(tagQB); //nicht in der Datenbank?
        Where<Media, Integer> whereMedia = mediaQB.where();

        whereMedia.like("filename", input).or().like("name", input)
                .or().like("description", input);
        if (date != null) whereMedia.or().eq("datetime", date);

        PreparedQuery<Media> preparedQuery = mediaQB.prepare();

        return dao.query(preparedQuery);
    }
}