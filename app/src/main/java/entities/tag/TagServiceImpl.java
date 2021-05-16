package entities.tag;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import entities.AbstractDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TagServiceImpl extends AbstractDAO implements TagService {
  private static final Logger logger = Logger.getLogger(TagServiceImpl.class.getName());
  private Dao<Tag, Integer> dao = null;

  public Dao<Tag, Integer> dao() {
    if (this.dao == null) {
      try {
        this.dao = DaoManager.createDao(AbstractDAO.source, Tag.class);
      } catch (SQLException e) {
        logger.log(Level.SEVERE, e.getMessage());
      }
    }

    return this.dao;
  }
}
