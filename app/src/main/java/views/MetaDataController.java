package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MetaDataController extends AbstractController implements Initializable {
  @FXML private TextField id;
  @FXML private TextField filename;
  @FXML private TextField name;
  @FXML private TextField description;
  @FXML private TextField resolution;
  @FXML private TextField location;
  @FXML private TextField datatype;
  @FXML private TextField albumName;
  @FXML private CheckBox isPrivate;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);

  }

  public void btnSaveDataClicked(ActionEvent actionEvent) {}
}
