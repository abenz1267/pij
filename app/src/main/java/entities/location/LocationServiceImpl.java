package entities.location;

import com.google.inject.Singleton;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import entities.AbstractEntityService;
import entities.media.Media;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@Singleton
public class LocationServiceImpl extends AbstractEntityService implements LocationService {
  private Dao<Location, Integer> _dao = null;

  public Dao<Location, Integer> dao() {
    if (this._dao == null) {
      try {
        this._dao = DaoManager.createDao(this.databaseConnectionService.get(), Location.class);
      } catch (SQLException e) {
        logger.log(Level.SEVERE, e.getMessage());
      }
    }

    return this._dao;
  }

  public void checkLocation(Media media) throws SQLException {
    if (media.getLocation() != null) {
      List<Location> res = dao().queryForEq("name", media.getLocation().getName());

      if (!res.isEmpty()) {
        media.setLocation(res.get(0));
      } else {
        dao().createIfNotExists(media.getLocation());
      }
    }
  }
}
