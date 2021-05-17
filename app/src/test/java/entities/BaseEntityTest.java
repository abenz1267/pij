package entities;

import com.j256.ormlite.table.TableUtils;
import entities.location.Location;
import entities.location.LocationService;
import entities.media.Media;
import entities.media.MediaService;
import entities.resolution.Resolution;
import entities.resolution.ResolutionService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import name.falgout.jeffrey.testing.junit.guice.GuiceExtension;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(GuiceExtension.class)
public abstract class BaseEntityTest {
  @Inject protected MediaService mediaService;
  @Inject protected LocationService locationService;
  @Inject protected ResolutionService resolutionService;
  @Inject protected DatabaseConnectionService databaseConnectionService;
  @Inject private Logger logger;

  private List<Class<?>> classes = new ArrayList<>();

  @BeforeAll
  public void setup() {
    this.classes.addAll(Arrays.asList(Media.class, Resolution.class, Location.class));

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
      for (Class<?> c : this.classes) {
        TableUtils.clearTable(databaseConnectionService.get(), c);
      }
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }

  @AfterAll
  public void cleanup() {
    try {
      TableUtils.dropTable(this.mediaService.dao(), true);
      TableUtils.dropTable(this.locationService.dao(), true);
      TableUtils.dropTable(this.resolutionService.dao(), true);
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }
}
