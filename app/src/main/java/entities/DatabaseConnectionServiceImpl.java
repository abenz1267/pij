package entities;

import com.google.inject.Singleton;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import entities.location.Location;
import entities.media.Media;
import entities.person.Person;
import entities.resolution.Resolution;
import entities.tag.Tag;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import resources.ResourceService;

@Singleton
public class DatabaseConnectionServiceImpl implements DatabaseConnectionService {
  @Inject private Logger logger;
  @Inject private ResourceService resourceService;
  private JdbcPooledConnectionSource connection;

  public void createSchema() {
    try {
      this.get();

      TableUtils.createTable(this.connection, Media.class);
      TableUtils.createTable(this.connection, Resolution.class);
      TableUtils.createTable(this.connection, Location.class);
      TableUtils.createTable(this.connection, Tag.class);
      TableUtils.createTable(this.connection, Person.class);

    } catch (SQLException e) {
      this.logger.log(Level.SEVERE, e.getMessage());
    }
  }

  public JdbcPooledConnectionSource get() {
    if (this.connection == null) {
      try {
        this.connection =
            new JdbcPooledConnectionSource("jdbc:sqlite:" + resourceService.getDatabaseFile());
      } catch (SQLException e) {
        logger.log(Level.SEVERE, e.getMessage());
      }
    }

    return this.connection;
  }

  public void close() {
    try {
      this.connection.close();
    } catch (IOException e) {
      logger.log(Level.INFO, e.getMessage());
    }
  }
}
