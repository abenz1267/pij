package entities.person;

import com.j256.ormlite.dao.Dao;

public interface PersonService {
  Dao<Person, Integer> dao();
}
