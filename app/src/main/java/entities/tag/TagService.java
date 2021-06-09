package entities.tag;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;

@ImplementedBy(TagServiceImpl.class)
public interface TagService {
  Dao<Tag, Integer> dao();
}
