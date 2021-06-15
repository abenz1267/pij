package views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class AlbumViewController extends AbstractController implements Initializable {
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }
}
