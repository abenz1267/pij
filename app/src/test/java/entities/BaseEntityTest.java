package entities;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import entities.location.Location;
import entities.location.LocationModule;
import entities.location.LocationService;
import entities.media.Media;
import entities.media.MediaModule;
import entities.media.MediaService;
import entities.resolution.Resolution;
import entities.resolution.ResolutionModule;
import entities.resolution.ResolutionService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public abstract class BaseEntityTest {
  @Inject protected MediaService mediaService;
  @Inject protected LocationService locationService;
  @Inject protected ResolutionService resolutionService;

  private static final Logger logger = Logger.getLogger(BaseEntityTest.class.getName());
  private JdbcPooledConnectionSource source = null;
  private List<Class<?>> classes = new ArrayList<>();

  @BeforeAll
  public void setup() {
    Injector i =
        Guice.createInjector(new MediaModule(), new LocationModule(), new ResolutionModule());
    i.injectMembers(this);

    this.classes.addAll(Arrays.asList(Media.class, Resolution.class, Location.class));

    try {
      this.source = new JdbcPooledConnectionSource("jdbc:sqlite:data.db");

      for (Class<?> c : this.classes) {
        TableUtils.createTable(this.source, c);
      }
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }

  @AfterEach
  public void clear() {
    try {
      this.source = new JdbcPooledConnectionSource("jdbc:sqlite:data.db");
      for (Class<?> c : this.classes) {
        TableUtils.clearTable(this.source, c);
      }
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }

  @AfterAll
  public void cleanup() {
    try {
      this.source = new JdbcPooledConnectionSource("jdbc:sqlite:data.db");
      TableUtils.dropTable(this.mediaService.dao(), true);
      TableUtils.dropTable(this.locationService.dao(), true);
      TableUtils.dropTable(this.resolutionService.dao(), true);
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }
}
