package entities.album;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import entities.AbstractEntityService;

import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.logging.Level;

@Singleton
public class AlbumServiceImpl extends AbstractEntityService implements AlbumService {

  private Dao<Album, Integer> dao = null;

  public Dao<Album, Integer> dao() {
    if (this.dao == null) {
      try {
        this.dao = DaoManager.createDao(this.databaseConnectionService.get(), Album.class);
      } catch (SQLException e) {
        logger.log(Level.SEVERE, e.getMessage());
      }
    }

    return this.dao;
  }

  private void createAlbum(Album album) throws SQLException {
    this.transaction(
        () -> {
          this.dao().createIfNotExists(album);

          return null;
        });
  }
}
