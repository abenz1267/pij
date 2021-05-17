package pij;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

  ResourceBundle bundle;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    this.bundle = ResourceBundle.getBundle("texts");

    setLabel_text();
  }

  @FXML Label label_text;

  public void setLabel_text() {
    this.label_text.setText(bundle.getString("label"));
  }
}
