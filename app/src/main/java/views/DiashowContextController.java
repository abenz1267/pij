package views;

import com.google.common.eventbus.Subscribe;
import events.AddToDiashow;
import events.PlayDiashow;
import events.SetUIState;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;

/**
 * Controller handling Diashow related events and FXML
 *
 * @author Christian Paulsen
 */
public class DiashowContextController extends AbstractController implements Initializable {
  @FXML VBox list;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }

  @Subscribe
  /**
   * Adds the media from the {@link AddToDiashow} event to a list in the context view. Items in that
   * list can be deleted.
   */
  private void add(AddToDiashow event) {
    this.addToList(event.getMedia(), list);
  }

  /** Create a new event to close the context box */
  @FXML
  private void closeDiashowContext() {
    eventService.post(new SetUIState(SetUIState.State.CLOSE_CONTEXT));
  }

  /** Create a new event to start a diashow with the media files from the context menu. */
  @FXML
  private void startDiashow() {
    this.eventService.post(new PlayDiashow(new ArrayList<>(this.itemList)));
  }
}
