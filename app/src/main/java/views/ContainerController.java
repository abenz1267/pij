package views;

import entities.media.Media;
import events.SetUIState;
import events.SetUIState.State;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;

public class ContainerController extends AbstractController implements Initializable {
  @FXML FlowPane contextContainer;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.eventService.register(this);
    this.eventService.post(new SetUIState(State.INITIAL));
  }
}
