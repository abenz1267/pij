package views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class AddMediaViewController extends AbstractController implements Initializable {
  @FXML private FlowPane flowPane;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  @FXML
  void addMedia(ActionEvent event) {

    closeStage(event);
  }

  private void closeStage(ActionEvent event) {
    Node source = (Node) event.getSource();
    var stage = (Stage) source.getScene().getWindow();
    stage.close();
  }
}
