package entities;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;

/**
 * Service to handle basic database interactions
 *
 * @author Andrej Benz
 */
@ImplementedBy(DatabaseConnectionServiceImpl.class)
public interface DatabaseConnectionService {
  JdbcPooledConnectionSource get();

  void createSchema();

  void close();
}
