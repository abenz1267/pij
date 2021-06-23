package views;

import events.SetUIState;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * Controller handling the context state of album.
 *
 * @author Timm Lohmann
 * @author Joey Wille
 * @author Phillip Knutzen
 */
public class AlbumContextController extends AbstractController implements Initializable {
  @FXML Label albumLabel;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // for abstract
  }

  @FXML
  private void addToAlbum(ActionEvent actionEvent) {
    eventService.post(new SetUIState(SetUIState.State.ADDTOALBUM));
  }
}
