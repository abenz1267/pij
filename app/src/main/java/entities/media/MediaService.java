package entities.media;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;
import java.io.File;
import java.io.IOException;
import java.util.List;

@ImplementedBy(MediaServiceImpl.class)
public interface MediaService {
  Dao<Media, Integer> dao();

  void importMedia(List<File> files) throws IOException;
}
