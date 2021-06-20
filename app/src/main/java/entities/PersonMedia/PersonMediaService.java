package entities.PersonMedia;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;

@ImplementedBy(PersonMediaServiceImpl.class)
public interface PersonMediaService {
    Dao<PersonMedia, Integer> dao();
}
