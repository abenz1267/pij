package entities.resolution;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import entities.AbstractDAO;
import java.sql.SQLException;

public class ResolutionServiceImpl extends AbstractDAO implements ResolutionService {
  private Dao<Resolution, Integer> dao = null;

  public Dao<Resolution, Integer> dao() {
    if (this.dao == null) {
      try {
        this.dao = DaoManager.createDao(AbstractDAO.source, Resolution.class);
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    }

    return this.dao;
  }
}
