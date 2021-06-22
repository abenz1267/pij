package entities.albummedia;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import entities.AbstractEntityService;
import entities.album.Album;
import entities.media.Media;
import entities.media.MediaService;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AlbumMediaServiceImpl extends AbstractEntityService implements AlbumMediaService {
  @Inject private MediaService mediaService;

  private Dao<AlbumMedia, Integer> iDao = null;

  public Dao<AlbumMedia, Integer> dao() {
    if (this.iDao == null) {
      try {
        this.iDao = DaoManager.createDao(this.databaseConnectionService.get(), AlbumMedia.class);
      } catch (SQLException e) {
        logger.log(Level.SEVERE, e.getMessage());
      }
    }

    return this.iDao;
  }

  @Override
  public List<Media> getMedia(Album album) throws SQLException {
    var qb = dao().queryBuilder();
    var mQb = mediaService.dao().queryBuilder();

    qb.selectColumns("media_id");
    qb.where().eq("album_id", album.getId());

    mQb.where().in("id", qb);

    return mQb.query();
  }
}
