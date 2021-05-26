package entities.media;

import com.google.common.io.Files;
import com.google.inject.Singleton;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import entities.AbstractEntityService;
import entities.location.Location;
import entities.location.LocationService;
import entities.media.Media.DataType;
import entities.resolution.Resolution;
import entities.resolution.ResolutionService;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.inject.Inject;

@Singleton
public class MediaServiceImpl extends AbstractEntityService implements MediaService {
  @Inject private LocationService locationService;
  @Inject private ResolutionService resolutionService;
  @Inject private Logger logger;
  private Dao<Media, Integer> dao = null;

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

  public void importMedia(List<File> files) throws IOException {
    for (File file : files) {
      var copied = new File("mediafiles", file.getName());
      com.google.common.io.Files.copy(file, copied);

      Media media = new Media();

      String ext = Files.getFileExtension(file.getName());
      media.setFilename(copied.getPath());
      media.setName(media.getFilename());

      DataType datatype = null;

      for (DataType d : DataType.values()) {
        if (d.toString().contains(ext)) {
          datatype = d;
        }
      }

      BufferedImage img = ImageIO.read(copied);

      Resolution res = new Resolution(img.getWidth(), img.getHeight());

      media.setDataType(datatype);
      media.setResolution(res);

      try {
        this.create(media);
      } catch (SQLException e) {
        logger.log(Level.INFO, e.getMessage());

        copied.delete();

        throw new IOException("Couldn't import image");
      }
    }
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
}
