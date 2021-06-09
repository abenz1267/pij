package entities.albummedia;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;

@ImplementedBy(AlbumMediaServiceImpl.class)
public interface AlbumMediaService {
  Dao<AlbumMedia, Integer> dao();
}
