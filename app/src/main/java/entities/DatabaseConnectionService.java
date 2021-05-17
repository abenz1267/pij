package entities;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;

@ImplementedBy(DatabaseConnectionServiceImpl.class)
public interface DatabaseConnectionService {
  JdbcPooledConnectionSource get();
}
