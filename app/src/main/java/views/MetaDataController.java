package views;

import com.google.common.eventbus.Subscribe;
import events.LoadMetaData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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
      locationField.setAlignment(Pos.CENTER_RIGHT);
    }

    idField.setText(String.valueOf(media.getId()));
    idField.setAlignment(Pos.CENTER_RIGHT);

    filenameField.setText(media.getFilename());
    filenameField.setAlignment(Pos.CENTER_RIGHT);

    nameField.setText(media.getName());
    nameField.setAlignment(Pos.CENTER_RIGHT);

    descriptionField.setText(media.getDescription());
    descriptionField.setAlignment(Pos.CENTER_RIGHT);

    datatypeField.setText(media.getDataType().toString());
    datatypeField.setAlignment(Pos.CENTER_RIGHT);

    resolutionField.setText(media.getResolution().toString());
    resolutionField.setAlignment(Pos.CENTER_RIGHT);

    isPrivateBox.setSelected(media.isPrivate());
    isPrivateBox.setAlignment(Pos.CENTER_RIGHT);

  }

  public void btnSaveDataClicked(ActionEvent event) {

  }
}
