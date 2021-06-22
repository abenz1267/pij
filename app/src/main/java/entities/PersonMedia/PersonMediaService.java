package entities.PersonMedia;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;
import entities.media.Media;
import entities.person.Person;

import java.sql.SQLException;
import java.util.List;

@ImplementedBy(PersonMediaServiceImpl.class)
public interface PersonMediaService {
  Dao<PersonMedia, Integer> dao();

  void remove(Person person, Media media) throws SQLException;

  List<PersonMedia> get(Person person, Media media) throws SQLException;

  List<Person> getPersons(Media media) throws SQLException;
}
