package entities.resolution;

import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;

public interface ResolutionService {
  Dao<Resolution, Integer> dao();

  void create(Resolution resolution) throws SQLException;
}
