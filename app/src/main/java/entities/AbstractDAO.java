package entities;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.misc.TransactionManager;
import java.sql.SQLException;
import java.util.concurrent.Callable;

public abstract class AbstractDAO {
  protected static JdbcPooledConnectionSource source = null;

  public AbstractDAO() {
    try {
      AbstractDAO.source = new JdbcPooledConnectionSource("jdbc:sqlite:data.db");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public void transaction(Callable<Void> action) throws SQLException {
    TransactionManager.callInTransaction(
        AbstractDAO.source,
        new Callable<Void>() {
          public Void call() throws Exception {
            action.call();
            return null;
          }
        });
  }
}
