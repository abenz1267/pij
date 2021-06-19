package views;

import com.google.common.eventbus.Subscribe;
import events.ShowImage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class MetaDataController extends AbstractController implements Initializable {
  @FXML private TextField id;
  @FXML private TextField filenameField;
  @FXML private TextField name;
  @FXML private TextField dateTime;
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

  @Subscribe
  public void getMetaData(ShowImage event) {
      var filename = event.getFilename();

    filenameField.clear();
    filenameField.setText(filename);
    logger.info(filename);
  }

  public void btnSaveDataClicked(ActionEvent actionEvent) {}
}
