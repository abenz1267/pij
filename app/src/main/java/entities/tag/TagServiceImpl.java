package entities.tag;

import com.google.inject.Singleton;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import entities.AbstractEntityService;
import java.sql.SQLException;
import java.util.logging.Level;

@Singleton
public class TagServiceImpl extends AbstractEntityService implements TagService {
  private Dao<Tag, Integer> iDao = null;

  public Dao<Tag, Integer> dao() {
    if (this.iDao == null) {
      try {
        this.iDao = DaoManager.createDao(this.databaseConnectionService.get(), Tag.class);
      } catch (SQLException e) {
        logger.log(Level.SEVERE, e.getMessage());
      }
    }

    return this.iDao;
  }
}
