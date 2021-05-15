package entities;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import java.sql.SQLException;

public abstract class AbstractDAO {
  protected static JdbcPooledConnectionSource source = null;

  public AbstractDAO() {
    try {
      AbstractDAO.source = new JdbcPooledConnectionSource("jdbc:sqlite:data.db");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
