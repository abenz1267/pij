package entities.albummedia;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;
import entities.album.Album;
import entities.media.Media;

import java.sql.SQLException;
import java.util.List;

@ImplementedBy(AlbumMediaServiceImpl.class)
public interface AlbumMediaService {
  Dao<AlbumMedia, Integer> dao();

  List<Media> getMedia(Album album) throws SQLException;
}
