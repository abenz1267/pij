package views;

import events.SetUIState;
import events.SetUIState.State;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * Container containing the contextwrapper and mainwrapper
 *
 * @author Andrej Benz
 */
public class ContainerController extends AbstractController implements Initializable {
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.eventService.register(this);
    this.eventService.post(new SetUIState(State.INITIAL));
  }
}
