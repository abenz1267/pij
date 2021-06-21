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

public class DiashowContextController extends AbstractController implements Initializable {
  @FXML VBox list;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }

  @Subscribe
  private void add(AddToDiashow event) {
    this.addToList(event.getMedia(), list);
  }

  @FXML
  private void closeDiashowContext() {
    eventService.post(new SetUIState(SetUIState.State.CLOSE_CONTEXT));
  }

  @FXML
  private void startDiashow() {
    this.eventService.post(new PlayDiashow(new ArrayList<>(this.itemList)));
  }
}
