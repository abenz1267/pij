package entities.media;

import com.j256.ormlite.dao.Dao;
import entities.ValidationException;
import java.sql.SQLException;

public interface MediaService {
  Dao<Media, Integer> dao();

  void create(Media media) throws SQLException, ValidationException;
}