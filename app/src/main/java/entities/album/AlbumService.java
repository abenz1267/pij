package entities.album;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;

@ImplementedBy(AlbumServiceImpl.class)
public interface AlbumService {
  Dao<Album, Integer> dao();


}
