package views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import resources.Resource;

public class MainController extends AbstractController implements Initializable {
  @FXML Label labelText;
  @FXML Button btn;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    resourceService.setText(this.labelText, Resource.GENERIC, "label");
  }

  @FXML
  private void next() throws Exception {
    sceneService.change(View.SECONDVIEW);
  }
}
