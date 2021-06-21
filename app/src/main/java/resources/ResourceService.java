package resources;

import com.google.inject.ImplementedBy;
import javafx.scene.control.Labeled;
import javafx.stage.Stage;

/**
 * Service to handl various resource-related tasks.
 *
 * @author Andrej Benz
 */
@ImplementedBy(ResourceServiceImpl.class)
public interface ResourceService {
  /**
   * Sets the text for a given {@link Labeled} element
   *
   * @param element the {@link Labeled} element
   * @param bundle the {@link Resource} bundle
   * @param prop the prop name
   */
  void setText(Labeled element, Resource bundle, String prop);

  /**
   * Sets the title for a given {@link Stage}
   *
   * @param stage the {@link Stage} element
   * @param bundle the {@link Resource} bundle
   * @param prop the prop name
   */
  void setStageTitle(Stage stage, Resource bundle, String prop);

  /**
   * Simply returns a value for a given resource prop.
   *
   * @param bundle the {@link Resource} bundle
   * @param prop the prop name
   * @return the result
   */
  String getString(Resource bundle, String prop);

  /**
   * Sets file/folder locations for the media directory and the database
   *
   * @param mediaDir the mediaDir
   * @param databaseFile the databaseFile
   */
  void setContentFiles(String mediaDir, String databaseFile);

  /**
   * Returns the media directory
   *
   * @return media directory
   */
  String getMediaDir();

  /**
   * Returns the database file
   *
   * @return database file
   */
  String getDatabaseFile();
}
