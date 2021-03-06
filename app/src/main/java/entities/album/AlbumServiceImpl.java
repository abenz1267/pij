package entities.album;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import entities.AbstractEntityService;
import entities.albummedia.AlbumMedia;
import entities.albummedia.AlbumMediaService;
import entities.media.Media;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AlbumServiceImpl extends AbstractEntityService implements AlbumService {
  @Inject private AlbumMediaService albumMediaService;

  private Dao<Album, Integer> iDao = null;

  public Dao<Album, Integer> dao() {
    if (this.iDao == null) {
      try {
        this.iDao = DaoManager.createDao(this.databaseConnectionService.get(), Album.class);
      } catch (SQLException e) {
        logger.log(Level.SEVERE, e.getMessage());
      }
    }

    return this.iDao;
  }

  public void createAlbum(Album album) throws SQLException {
    this.dao().createIfNotExists(album);
  }

  public void addMedia(Album album, Media... media) throws SQLException {
    for (Media item : media) {
      this.albumMediaService.dao().createIfNotExists(new AlbumMedia(album, item));
    }
  }
}
