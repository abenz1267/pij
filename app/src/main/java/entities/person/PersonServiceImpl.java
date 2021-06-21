package entities.person;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import entities.AbstractEntityService;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.inject.Singleton;

@Singleton
public class PersonServiceImpl extends AbstractEntityService implements PersonService {
  private Dao<Person, Integer> _dao = null;

  public Dao<Person, Integer> dao() {
    if (this._dao == null) {
      try {
        this._dao = DaoManager.createDao(this.databaseConnectionService.get(), Person.class);
      } catch (SQLException e) {
        logger.log(Level.SEVERE, e.getMessage());
      }
    }

    return this._dao;
  }
}
