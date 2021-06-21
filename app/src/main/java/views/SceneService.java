package views;

import com.google.inject.ImplementedBy;
import events.SetUIState.State;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

/**
 * Service to handle various scene-related tasks
 *
 * @author Andrej Benz
 */
@ImplementedBy(SceneServiceImpl.class)
public interface SceneService {
  /**
   * Returns a pane for a given {@link View}.
   *
   * @return {@link Pane} the pane
   */
  Pane load(View view) throws IOException;

  /**
   * Returns the current window.
   *
   * @return {@link Window} the window
   */
  Window getWindow();

  /**
   * Sets the root scene for the program
   *
   * @param scene the {@link Scene}
   */
  void setRootScene(Scene scene);

  /**
   * Sets content for a pane.
   *
   * @param pane the {@link Pane}
   * @param view the {@link View}
   * @throws IOException if the view can't be loaded
   */
  void setContent(Pane pane, View view) throws IOException;

  /**
   * Returns the current UI {@link State}
   *
   * @return {@link State}
   */
  State getState();

  /**
   * Sets the current UI {@link State}
   *
   * @param state the {@link State}
   */
  void setState(State state);
}
