package entities.location;

import com.google.inject.Singleton;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import entities.AbstractEntityService;
import java.sql.SQLException;
import java.util.logging.Level;

@Singleton
public class LocationServiceImpl extends AbstractEntityService implements LocationService {
  private Dao<Location, Integer> dao = null;

  public Dao<Location, Integer> dao() {
    if (this.dao == null) {
      try {
        this.dao = DaoManager.createDao(this.databaseConnectionService.get(), Location.class);
      } catch (SQLException e) {
        logger.log(Level.SEVERE, e.getMessage());
      }
    }

    return this.dao;
  }
}
