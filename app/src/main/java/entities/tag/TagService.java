package entities.tag;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;

/**
 * Service to handle tag entitites.
 *
 * @author Timm Lohmann
 * @author Phillip Knutzen
 * @author Joey Wille
 */
@ImplementedBy(TagServiceImpl.class)
public interface TagService {
  Dao<Tag, Integer> dao();
}
