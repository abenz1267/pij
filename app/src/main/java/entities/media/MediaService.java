package entities.media;

import com.google.inject.ImplementedBy;
import com.j256.ormlite.dao.Dao;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

/**
 * Service to handle media entities.
 *
 * @author Andrej Benz
 * @author Huilun Chen
 */
@ImplementedBy(MediaServiceImpl.class)
public interface MediaService {
  Dao<Media, Integer> dao();

  /**
   * Imports a given list of files.
   *
   * @param files list of files to import
   * @throws IOException if there's a problem with the files
   * @throws SQLException if there's a problem with the database.
   * @author Andrej Benz
   */
  void importMedia(List<File> files) throws IOException, SQLException;

  /**
   * Imports a given exported *.zip file.
   *
   * @param file the *.zip file.
   * @throws IOException if there's a problem with the files
   * @throws SQLException if there's a problem with the database.
   * @author Andrej Benz
   */
  void importMediaFromExport(File file) throws IOException, SQLException;

  /**
   * Exports a given list of {@link Media} files as *.zip, including all database information.
   *
   * @param media the {@link Media} files
   * @param output the destination path for the exported *.zip.
   * @throws IOException if there's a problem with the files
   * @throws SQLException if there's a problem with the database.
   * @author Andrej Benz
   */
  void exportMedia(List<Media> media, Path output) throws SQLException, IOException;

  /**
   * Defines if the orignal file should be kept in place when importing.
   *
   * @param val true or false.
   * @author Andrej Benz
   */
  void setKeepOriginal(boolean val);

  /**
   * Refreshes all entities for a {@link Media} entity.
   *
   * @param media the media entity.
   * @author Andrej Benz
   */
  void refreshAll(Media media);

  /**
   * Persists a media entity into the database. Creates missing related entities, if needed.
   *
   * @param media the media entity.
   * @throws SQLException if there's a problem with the database.
   * @author Andrej Benz
   */
  void create(Media media) throws SQLException;

  void update(Media media) throws SQLException;

  List<Media> filterMediaByInput(String input) throws SQLException;
}
