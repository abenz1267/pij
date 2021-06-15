package views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AlbumViewController extends AbstractController implements Initializable {
  @FXML private FlowPane flowPane;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);

    flowPane.getChildren().clear();
    flowPane.setPadding(new Insets(4));
    flowPane.setHgap(4);
  }
}
