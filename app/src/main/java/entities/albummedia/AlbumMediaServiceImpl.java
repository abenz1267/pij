package entities.albummedia;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import entities.AbstractEntityService;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.inject.Singleton;

@Singleton
public class AlbumMediaServiceImpl extends AbstractEntityService implements AlbumMediaService {
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
}
