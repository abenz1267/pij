package views;

import com.google.common.eventbus.Subscribe;
import events.LoadMetaData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class MetaDataController extends AbstractController implements Initializable {
  @FXML private TextField id;
  @FXML private TextField filenameField;
  @FXML private TextField name;
  @FXML private DatePicker dateTimePicker;
  @FXML private TextField description;
  @FXML private TextField resolutionField;
  @FXML private TextField location;
  @FXML private TextField datatype;
  @FXML private TextField albumName;
  @FXML private CheckBox isPrivate;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }

  @Subscribe
  public void getMetaData(LoadMetaData event) {
    var media = event.getMedia();
    mediaService.refreshAll(media);
    if (media.getDatetime() != null) {
      var input = new Date();
      var date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      dateTimePicker.setValue(date);
    }

    filenameField.setText(media.getFilename());

    resolutionField.setText(event.getMedia().getResolution().toString());

    }

  public void btnSaveDataClicked(ActionEvent actionEvent) {}
}
