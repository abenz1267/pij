package entities.person;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;
import entities.media.Media;

import java.sql.SQLException;

@ImplementedBy(PersonServiceImpl.class)
public interface PersonService {
  Dao<Person, Integer> dao();
}
