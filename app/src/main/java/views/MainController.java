package views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javax.inject.Inject;
import resources.Resource;
import resources.ResourceService;

public class MainController implements Initializable {
  @FXML Label labelText;
  @Inject ResourceService resourceService;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    resourceService.setText(this.labelText, Resource.GENERIC, "label");
  }
}
