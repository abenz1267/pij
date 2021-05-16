package entities;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.misc.TransactionManager;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractDAO {
  protected static JdbcPooledConnectionSource source;
  private static final Logger logger = Logger.getLogger(AbstractDAO.class.getName());

  static {
    try {
      AbstractDAO.source = new JdbcPooledConnectionSource("jdbc:sqlite:data.db");
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.getMessage());
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
