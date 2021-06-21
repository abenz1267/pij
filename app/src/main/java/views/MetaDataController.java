package views;

import com.google.common.eventbus.Subscribe;
import entities.media.Media;
import entities.person.Person;
import events.LoadMetaData;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import static java.time.ZoneOffset.UTC;

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
  @FXML private VBox personBox;

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
      var date = media.getDatetime();
      var localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      datetimePicker.setValue(localDate);
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

    this.listPersons(media);
  }

  @FXML
  public void btnSaveDataClicked(ActionEvent event) {
    this.media.setName(nameField.getText().trim());
    this.media.setDescription(descriptionField.getText());
    this.media.setPrivate(isPrivateBox.isSelected());
//    this.media.setLocation(locationField.getText().trim());

    var localDate = datetimePicker.getValue();
    var date = Date.from(localDate.atStartOfDay().toInstant(ZoneOffset.UTC));
    this.media.setDatetime(date);


    try {
      mediaService.update(media);
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }

  @FXML
  public void addPerson(ActionEvent event) {
    if (personFieldFirstName.getText().isEmpty() || personFieldLastName.getText().isEmpty()) {
      return;
    }

    var person = new Person();
    person.setFirstname(personFieldFirstName.getText().trim());
    person.setLastname(personFieldLastName.getText().trim());
    media.getPersons().add(person);
    try {
      mediaService.update(media);
      mediaService.refreshAll(media);

      personFieldLastName.clear();
      personFieldFirstName.clear();

      this.listPersons(media);
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }

  private void listPersons(Media media) {
    var children = personBox.getChildren();
    children.clear();

    media.setPersons(new ArrayList<>());
    mediaService.refreshAll(media);

    for (var p : media.getPersons()) {
      var wrapper = new HBox();
      var label = new Label(p.toString());
      var button = new Button();
      button.setGraphic(new FontIcon("mdi2d-delete"));

      button.setOnAction(
              event -> {
                try {
                  personMediaService.remove(p, media);
                  listPersons(media);
                } catch (SQLException e) {
                  logger.log(Level.SEVERE, e.getMessage());
                }
              }
      );

      wrapper.getChildren().add(label);
      wrapper.getChildren().add(button);
      children.add(wrapper);
    }
  }
}
