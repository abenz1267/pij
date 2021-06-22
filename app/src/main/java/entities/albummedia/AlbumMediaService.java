package entities.albummedia;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;
import entities.album.Album;
import entities.media.Media;

import java.sql.SQLException;
import java.util.List;

/**
 * Service to handle albumMedia.
 *
 * @author Timm Lohmann
 * @author Phillip Knutzen
 * @author Joey Wille
 */

@ImplementedBy(AlbumMediaServiceImpl.class)
public interface AlbumMediaService {
  Dao<AlbumMedia, Integer> dao();

  /**
   * Gets a given List of Album.
   * @param album the Album files.
   * @throws SQLException if there's a problem with the Database.
   * @author Timm Lohmann
   * @author Phillip Knutzen
   * @author Joey Wille
   */
  List<Media> getMedia(Album album) throws SQLException;
}
