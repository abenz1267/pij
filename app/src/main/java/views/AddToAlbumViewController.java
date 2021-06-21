package views;

import com.google.common.eventbus.Subscribe;
import events.AddToAlbum;
import events.SetUIState;
import events.SetUIState.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller handling the context to add media to an album.
 *
 * @author Phillip Knutzen
 * @author Timm Lohmann
 */

public class AddToAlbumViewController extends AbstractController implements Initializable {
  @FXML VBox list;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }

  @Subscribe
  private void add(AddToAlbum event) {
    this.addToList(event.getMedia(), list);
  }

  @FXML
  private void closeAdd() {
    eventService.post(new SetUIState(State.CLOSE_CONTEXT));
  }

  public void addToAlbum(ActionEvent actionEvent) {

  }
}
