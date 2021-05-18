package entities.tag;

import com.j256.ormlite.dao.Dao;

public interface TagService {
  Dao<Tag, Integer> dao();
}
