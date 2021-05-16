package entities.location;

import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;

public interface LocationService {
  Dao<Location, Integer> dao();

  void create(Location location) throws SQLException;
}
