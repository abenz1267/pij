package entities.resolution;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import entities.AbstractDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResolutionServiceImpl extends AbstractDAO implements ResolutionService {
  private static final Logger logger = Logger.getLogger(ResolutionServiceImpl.class.getName());
  private Dao<Resolution, Integer> dao = null;

  public Dao<Resolution, Integer> dao() {
    if (this.dao == null) {
      try {
        this.dao = DaoManager.createDao(AbstractDAO.source, Resolution.class);
      } catch (SQLException e) {
        logger.log(Level.SEVERE, e.getMessage());
      }
    }

    return this.dao;
  }
}
