package entities.location;

import com.j256.ormlite.dao.Dao;

public interface LocationService {
  Dao<Location, Integer> dao();
}
