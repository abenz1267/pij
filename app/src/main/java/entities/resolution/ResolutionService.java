package entities.resolution;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;

@ImplementedBy(ResolutionServiceImpl.class)
public interface ResolutionService {
  Dao<Resolution, Integer> dao();
}
