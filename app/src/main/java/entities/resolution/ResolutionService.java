package entities.resolution;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;
import entities.media.Media;
import java.sql.SQLException;

@ImplementedBy(ResolutionServiceImpl.class)
public interface ResolutionService {
  Dao<Resolution, Integer> dao();

  /**
   * Checks if resolution is already in database. Persists new entity if not, sets media resolution
   * to entity if yes.
   *
   * @author Andrej Benz
   */
  void checkResolution(Media media) throws SQLException;
}
