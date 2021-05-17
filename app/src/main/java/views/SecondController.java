package views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import resources.Resource;

public class SecondController extends AbstractController implements Initializable {
  @FXML Label labelText;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    resourceService.setText(this.labelText, Resource.GENERIC, "label2");
  }
}
