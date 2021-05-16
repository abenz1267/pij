package entities.person;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import entities.AbstractDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonServiceImpl extends AbstractDAO implements PersonService {
  private static final Logger logger = Logger.getLogger(PersonServiceImpl.class.getName());
  private Dao<Person, Integer> dao = null;

  public Dao<Person, Integer> dao() {
    if (this.dao == null) {
      try {
        this.dao = DaoManager.createDao(AbstractDAO.source, Person.class);
      } catch (SQLException e) {
        logger.log(Level.SEVERE, e.getMessage());
      }
    }

    return this.dao;
  }
}
