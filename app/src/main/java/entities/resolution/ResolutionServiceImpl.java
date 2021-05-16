package entities.resolution;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import entities.AbstractDAO;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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

  public void create(Resolution resolution) throws SQLException {
    Map<String, Object> kv = new HashMap<String, Object>();
    kv.put("height", resolution.getHeight());
    kv.put("width", resolution.getWidth());

    if (!this.dao().queryForFieldValuesArgs(kv).isEmpty()) {
      return;
    }

    this.dao().createIfNotExists(resolution);
  }
}
