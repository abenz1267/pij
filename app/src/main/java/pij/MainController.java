package pij;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

  ResourceBundle bundle;

  @FXML Label labelText;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    this.bundle = ResourceBundle.getBundle("texts");

    setLabelText();
  }

  public void setLabelText() {
    this.labelText.setText(bundle.getString("label"));
  }
}
