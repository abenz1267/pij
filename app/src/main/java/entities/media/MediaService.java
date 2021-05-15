package entities.media;

import com.j256.ormlite.dao.Dao;

public interface MediaService {
  Dao<Media, Integer> dao();
}
