package entities.media;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

@ImplementedBy(MediaServiceImpl.class)
public interface MediaService {
  Dao<Media, Integer> dao();

  void importMedia(List<File> files) throws IOException, SQLException;

  void importMediaFromExport(File file) throws IOException, SQLException;

  void exportMedia(List<Media> media, Path output) throws SQLException, IOException;

  void setKeepOriginal(boolean val);

  List<Media> filterMediaByInput(String input) throws SQLException;
}
