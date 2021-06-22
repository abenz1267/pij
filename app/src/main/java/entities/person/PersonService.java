package entities.person;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;

/**
 * Service to handle person entities.
 *
 * @author Timm Lohmann
 * @author Phillip Knutzen
 * @author Joey Wille
 */
@ImplementedBy(PersonServiceImpl.class)
public interface PersonService {
  Dao<Person, Integer> dao();
}
