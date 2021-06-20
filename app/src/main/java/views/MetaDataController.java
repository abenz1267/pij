package views;

import com.google.common.eventbus.Subscribe;
import entities.media.Media;
import entities.person.Person;
import events.LoadMetaData;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MetaDataController extends AbstractController implements Initializable {
  @FXML private TextField nameField;
  @FXML private DatePicker datetimePicker;
  @FXML private TextField descriptionField;
  @FXML private Label resolutionField;
  @FXML private TextField locationField;
  @FXML private Label datatypeField;
  @FXML private CheckBox isPrivateBox;
  @FXML private TextField personFieldFirstName;
  @FXML private TextField personFieldLastName;

  private Media media;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }

  @Subscribe
  public void getMetaData(LoadMetaData event) {
    var media = event.getMedia();
    mediaService.refreshAll(media);
    this.media = media;

    if (media.getDatetime() != null) {
      var input = new Date();
      var date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      datetimePicker.setValue(date);
    }

    if (media.getLocation() != null) {
      locationField.setText(media.getLocation().toString());
      locationField.setAlignment(Pos.CENTER_RIGHT);
    }

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

  @FXML
  public void btnSaveDataClicked(ActionEvent event) {
    this.media.setName(nameField.getText().trim());
    try {
      mediaService.update(media);
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }

  @FXML
  public void addPerson(ActionEvent event) {
    var person = new Person();
    person.setFirstname(personFieldFirstName.getText().trim());
    person.setLastname(personFieldLastName.getText().trim());
    media.getPersons().add(person);
    try {
      mediaService.update(media);
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }
}
