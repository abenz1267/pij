package entities.location;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;

@ImplementedBy(LocationServiceImpl.class)
public interface LocationService {
  Dao<Location, Integer> dao();
}
