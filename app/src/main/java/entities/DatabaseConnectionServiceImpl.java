package entities;

import com.google.inject.Singleton;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
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
    } catch (SQLException e) {
      this.logger.log(Level.SEVERE, e.getMessage());
    }
  }

  public JdbcPooledConnectionSource get() {
    return this.connection;
  }
}
