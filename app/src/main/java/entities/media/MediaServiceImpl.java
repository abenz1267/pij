package entities.media;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import entities.AbstractDAO;
import java.sql.SQLException;

public class MediaServiceImpl extends AbstractDAO implements MediaService {
  private Dao<Media, Integer> dao = null;

  public Dao<Media, Integer> dao() {
    if (this.dao == null) {
      try {
        this.dao = DaoManager.createDao(AbstractDAO.source, Media.class);
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    }

    return this.dao;
  }
}
