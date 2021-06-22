package entities.album;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;
import entities.media.Media;
import java.sql.SQLException;
import java.util.List;

/**
 * Service to handle albums.
 *
 * @author Timm Lohmann
 * @author Phillip Knutzen
 * @author Joey Wille
 */
@ImplementedBy(AlbumServiceImpl.class)
public interface AlbumService {
  Dao<Album, Integer> dao();

  /**
   * Persists a album entity into the database. Creates missing related entities, if needed.
   *
   * @param album the album entity.
   * @throws SQLException if there's a problem with the database.
   */
  void createAlbum(Album album) throws SQLException;

  /**
   * Persists the connection between media and album entities into the database to add media files to an album.
   *
   * @param album the album entity.
   * @param media the media entity.
   * @throws SQLException if there's a problem with the database.
   */
  void addMedia(Album album, Media... media) throws SQLException;

}
