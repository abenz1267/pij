package views;

import events.TestEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class TopMenuController extends AbstractController implements Initializable {
  @FXML Button importBtn;
  @FXML Button exportBtn;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    exportBtn.getStyleClass().add("btn--disabled");
  }

  @FXML
  private void enable() {
    exportBtn.getStyleClass().remove("btn--disabled");
    this.eventService.post(new TestEvent("Der text kommt von einem anderen Controller"));
  }
}
