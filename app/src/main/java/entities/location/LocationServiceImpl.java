package entities.location;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import entities.AbstractDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocationServiceImpl extends AbstractDAO implements LocationService {
  private static final Logger logger = Logger.getLogger(LocationServiceImpl.class.getName());
  private Dao<Location, Integer> dao = null;

  public Dao<Location, Integer> dao() {
    if (this.dao == null) {
      try {
        this.dao = DaoManager.createDao(AbstractDAO.source, Location.class);
      } catch (SQLException e) {
        logger.log(Level.SEVERE, e.getMessage());
      }
    }

    return this.dao;
  }
}
