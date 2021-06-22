package entities.location;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;
import entities.media.Media;
import java.sql.SQLException;

/**
 * Service to handle location entities.
 *
 * @author Andrej Benz
 */
@ImplementedBy(LocationServiceImpl.class)
public interface LocationService {
  Dao<Location, Integer> dao();

  /**
   * Checks if a given location is in the database. Persists new location if not, sets media
   * location to entity if yes.
   *
   * @param media the {@link Media}
   * @throws SQLException in case of a problem with the database
   * @author Andrej Benz
   */
  void checkLocation(Media media) throws SQLException;
}
