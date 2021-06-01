package entities;

import com.j256.ormlite.table.TableUtils;
import entities.album.Album;
import entities.album.AlbumService;
import entities.albumMedia.AlbumMedia;
import entities.location.Location;
import entities.location.LocationService;
import entities.media.Media;
import entities.media.MediaService;
import entities.resolution.Resolution;
import entities.resolution.ResolutionService;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import name.falgout.jeffrey.testing.junit.guice.GuiceExtension;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import resources.ResourceService;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(GuiceExtension.class)
public abstract class BaseEntityTest {
  @Inject protected MediaService mediaService;
  @Inject protected LocationService locationService;
  @Inject protected ResolutionService resolutionService;
  @Inject protected ResourceService resourceService;
  @Inject protected DatabaseConnectionService databaseConnectionService;
  @Inject protected AlbumService albumService;
  @Inject private Logger logger;

  private List<Class<?>> classes = new ArrayList<>();
  private static String MEDIA_FOLDER = "testmediafiles";
  private static String DATABASE = "test.db";

  @BeforeAll
  public void setup() {
    var folder = new File(MEDIA_FOLDER);
    folder.mkdir();

    resourceService.setContentFiles(MEDIA_FOLDER, DATABASE);
    mediaService.setKeepOriginal(true);

    databaseConnectionService.createSchema();

    this.classes.addAll(
        Arrays.asList(
            Media.class, Resolution.class, Location.class, Album.class, AlbumMedia.class));

    try {
      for (Class<?> c : this.classes) {
        TableUtils.createTable(databaseConnectionService.get(), c);
      }
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }

  @AfterEach
  public void clear() {
    try {
      FileUtils.deleteDirectory(new File(MEDIA_FOLDER));
      for (Class<?> c : this.classes) {
        TableUtils.clearTable(databaseConnectionService.get(), c);
      }
    } catch (IOException | SQLException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }

  @AfterAll
  public void cleanup() {
    databaseConnectionService.close();

    File db = new File(DATABASE);
    db.delete();
  }
}
