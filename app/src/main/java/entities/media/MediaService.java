package entities.media;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;

@ImplementedBy(MediaServiceImpl.class)
public interface MediaService {
  Dao<Media, Integer> dao();

  void create(Media media) throws SQLException;
}
