package entities.resolution;

import com.google.inject.Singleton;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import entities.AbstractEntityService;
import entities.media.Media;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

@Singleton
public class ResolutionServiceImpl extends AbstractEntityService implements ResolutionService {
  private Dao<Resolution, Integer> iDao = null;

  public Dao<Resolution, Integer> dao() {
    if (this.iDao == null) {
      try {
        this.iDao = DaoManager.createDao(this.databaseConnectionService.get(), Resolution.class);
      } catch (SQLException e) {
        logger.log(Level.SEVERE, e.getMessage());
      }
    }

    return this.iDao;
  }

  public void checkResolution(Media media) throws SQLException {
    Map<String, Object> kv = new HashMap<>();
    kv.put("height", media.getResolution().getHeight());
    kv.put("width", media.getResolution().getWidth());

    List<Resolution> res = dao().queryForFieldValuesArgs(kv);

    if (!res.isEmpty()) {
      media.setResolution(res.get(0));
    } else {
      dao().createIfNotExists(media.getResolution());
    }
  }
}
