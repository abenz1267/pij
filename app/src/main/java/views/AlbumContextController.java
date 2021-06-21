package views;

import java.net.URL;
import java.util.ResourceBundle;

import events.SetUIState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class AlbumContextController extends AbstractController implements Initializable {
  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  @FXML
  private void addToAlbum(ActionEvent actionEvent) {
    eventService.post(new SetUIState(SetUIState.State.ADDTOALBUM));
  }
}
