package entities.resolution;

import com.j256.ormlite.dao.Dao;

public interface ResolutionService {
  Dao<Resolution, Integer> dao();
}
