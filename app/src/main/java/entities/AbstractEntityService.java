package entities;

import com.j256.ormlite.misc.TransactionManager;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import javax.inject.Inject;

public abstract class AbstractEntityService {
  @Inject protected Logger logger;
  @Inject protected DatabaseConnectionService databaseConnectionService;

  protected void transaction(Callable<Void> action) throws SQLException {
    TransactionManager.callInTransaction(
        this.databaseConnectionService.get(),
        new Callable<Void>() {
          public Void call() throws Exception {
            action.call();
            return null;
          }
        });
  }
}
