package views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import resources.Resource;

public class MainController extends AbstractController implements Initializable {
  @FXML Label labelText;
  @FXML Button importBtn;
  @FXML Button exportBtn;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    exportBtn.getStyleClass().add("btn--disabled");
    resourceService.setText(this.labelText, Resource.GENERIC, "label");
  }

  @FXML
  private void enable() {
    exportBtn.getStyleClass().remove("btn--disabled");
  }

  @FXML
  private void next() throws IOException {
    sceneService.change(View.SECONDVIEW);
  }
}
