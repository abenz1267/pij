package entities.albummedia;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;
import entities.album.Album;
import entities.media.Media;
import java.sql.SQLException;
import java.util.List;

/**
 * Service to handle albumMedia entity.
 *
 * @author Timm Lohmann
 * @author Phillip Knutzen
 * @author Joey Wille
 */
@ImplementedBy(AlbumMediaServiceImpl.class)
public interface AlbumMediaService {
  Dao<AlbumMedia, Integer> dao();

  /**
   * Gets a List of given {@link Media} entitites which are connected with a given {@link Album}
   * entity.
   *
   * @param album the Album entities.
   * @throws SQLException if there's a problem with the Database.
   */
  List<Media> getMedia(Album album) throws SQLException;
}
