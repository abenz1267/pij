package entities.personmedia;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;
import entities.media.Media;
import entities.person.Person;
import java.sql.SQLException;
import java.util.List;

/**
 * Service to handle personMedia entities.
 *
 * @author Timm Lohmann
 * @author Phillip Knutzen
 * @author Joey Wille
 */
@ImplementedBy(PersonMediaServiceImpl.class)
public interface PersonMediaService {
  Dao<PersonMedia, Integer> dao();

  /**
   * Removes a person entity from person media and removes person from person if the person doesnt
   * exist in person media database.
   *
   * @param person the person entity. media the media entity.
   * @throws SQLException if there's a problem with the database.
   * @author Phillip Knutzen
   */
  void remove(Person person, Media media) throws SQLException;

  /**
   * Gets a List of connections between {@link Person} entitites and {@link Media} entities.
   *
   * @param person the person entities.
   * @throws SQLException if there's a problem with the Database.
   */
  List<PersonMedia> get(Person person, Media media) throws SQLException;

  /**
   * Gets a List a List with {@link Person} entities which are connected to a given {@link Media}
   * entity.
   *
   * @param media the media entities.
   * @return returns the given list.
   * @throws SQLException
   */
  List<Person> getPersons(Media media) throws SQLException;
}
