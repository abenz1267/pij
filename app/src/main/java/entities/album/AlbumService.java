package entities.album;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;
import entities.media.Media;
import java.sql.SQLException;
import java.util.List;

@ImplementedBy(AlbumServiceImpl.class)
public interface AlbumService {
  Dao<Album, Integer> dao();

  void createAlbum(Album album) throws SQLException;

  void addMedia(Album album, Media... media) throws SQLException;


}
