package entities.PersonMedia;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;
import entities.media.Media;
import entities.person.Person;

import java.sql.SQLException;
import java.util.List;

/**
 * Service to handle personMedias.
 *
 * @author Timm Lohmann
 * @author Phillip Knutzen
 * @author Joey Wille
 */

@ImplementedBy(PersonMediaServiceImpl.class)
public interface PersonMediaService {
  Dao<PersonMedia, Integer> dao();

  /**
   * Removes a person entity from person media and removes person from person if the person doesnt exist in person media database.
   *
   * @param person the person entity. media the media entity.
   * @throws SQLException if there's a problem with the database.
   * @author Phillip Knutzen
   */

  void remove(Person person, Media media) throws SQLException;

  List<PersonMedia> get(Person person, Media media) throws SQLException;

  List<Person> getPersons(Media media) throws SQLException;
}
