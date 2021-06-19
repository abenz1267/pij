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
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class MetaDataController extends AbstractController implements Initializable {
  @FXML private TextField idField;
  @FXML private TextField filenameField;
  @FXML private TextField nameField;
  @FXML private DatePicker datetimePicker;
  @FXML private TextField descriptionField;
  @FXML private TextField resolutionField;
  @FXML private TextField locationField;
  @FXML private TextField datatypeField;
  @FXML private CheckBox isPrivateBox;

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
      datetimePicker.setValue(date);
    }

    if (media.getLocation() != null) {
      locationField.setText(media.getLocation().toString());
    }

    idField.setText(String.valueOf(media.getId()));
    filenameField.setText(media.getFilename());
    nameField.setText(media.getName());
    descriptionField.setText(media.getDescription());
    datatypeField.setText(media.getDataType().toString());
    resolutionField.setText(media.getResolution().toString());
    isPrivateBox.setSelected(media.isPrivate());

  }

  public void btnSaveDataClicked(ActionEvent event) {

  }
}
