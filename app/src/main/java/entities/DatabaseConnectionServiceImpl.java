package entities;

import com.google.inject.Singleton;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import entities.location.Location;
import entities.media.Media;
import entities.person.Person;
import entities.resolution.Resolution;
import entities.tag.Tag;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

@Singleton
public class DatabaseConnectionServiceImpl implements DatabaseConnectionService {
  private Logger logger;
  private JdbcPooledConnectionSource connection;

  @Inject
  DatabaseConnectionServiceImpl(Logger logger) {
    this.logger = logger;

    try {
      this.connection = new JdbcPooledConnectionSource("jdbc:sqlite:data.db");

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
    return this.connection;
  }
}
