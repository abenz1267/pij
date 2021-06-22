package entities.PersonMedia;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import entities.AbstractEntityService;
import entities.media.Media;
import entities.person.Person;
import entities.person.PersonService;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PersonMediaServiceImpl extends AbstractEntityService implements PersonMediaService {

  @Inject private PersonService personService;

  private Dao<PersonMedia, Integer> dao = null;

  public Dao<PersonMedia, Integer> dao() {
    if (this.dao == null) {
      try {
        this.dao = DaoManager.createDao(this.databaseConnectionService.get(), PersonMedia.class);
      } catch (SQLException e) {
        logger.log(Level.SEVERE, e.getMessage());
      }
    }

    return this.dao;
  }

  @Override
  public void remove(Person person, Media media) throws SQLException {
    var result = get(person, media);

    dao().delete(result.get(0));
    var others = dao().queryForEq("person_id", person.getId());
    if (others.isEmpty()) {
      personService.dao().delete(person);
    }
  }

  @Override
  public List<PersonMedia> get(Person person, Media media) throws SQLException {

    var qb = dao().queryBuilder();
    qb.where().eq("person_id", person.getId()).and().eq("media_id", media.getId());

    return qb.query();
  }

  @Override
  public List<Person> getPersons(Media media) throws SQLException {
    var qb = dao().queryBuilder();
    var mQb = personService.dao().queryBuilder();

    qb.selectColumns("person_id");
    qb.where().eq("media_id", media.getId());

    mQb.where().in("id", qb);

    return mQb.query();
  }
}
