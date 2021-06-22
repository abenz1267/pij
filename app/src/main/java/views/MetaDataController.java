package views;

import com.google.common.eventbus.Subscribe;
import entities.location.Location;
import entities.media.Media;
import entities.person.Person;
import entities.tag.Tag;
import events.LoadMetaData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;

/**
 * Controller to handle metadata view
 *
 * @author Timm Lohmann
 * @author Phillip Knutzen
 * @author Joey Wille
 */
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
  @FXML private ComboBox tagBox;
  @FXML private TilePane tagPane;

  private Media media;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);

    try {
      tagService
          .dao()
          .queryForAll()
          .forEach(
              tag -> {
                tagBox.getItems().add(tag.getName());
              });

      tagBox.setEditable(true);
      tagBox.setOnAction((event) -> {
        var sel = tagBox.getSelectionModel().getSelectedItem().toString();
        logger.info("selected Tag:" + sel);
      });
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
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
    this.media.setName(nameField.getText());
    this.media.setDescription(descriptionField.getText());
    this.media.setPrivate(isPrivateBox.isSelected());

    var location = new Location();
    location.setName(locationField.getText());
    this.media.setLocation(location);

    var localDate = datetimePicker.getValue();
    if (localDate != null) {
      var date = Date.from(localDate.atStartOfDay().toInstant(ZoneOffset.UTC));
      this.media.setDatetime(date);
    }

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
    person.setFirstname(personFieldFirstName.getText());
    person.setLastname(personFieldLastName.getText());
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

  @FXML
  public void addTag() {
    var tag = new Tag();
    tag.setName(tagBox.getValue().toString());
    media.getTags().add(tag);

    try {
      mediaService.update(media);
      mediaService.refreshAll(media);

      this.listTags(media);
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }

  private void listTags(Media media) {
    var children = tagPane.getChildren();
    children.clear();

    media.setTags(new ArrayList<>());
    mediaService.refreshAll(media);

    for (var t : media.getTags()) {
      var wrapper = new HBox();
      var button = new Button();
      button.setGraphic(new FontIcon("mdi2b-bookmark-remove-outline"));
      button.setText(t.getName());

      button.setOnAction(
          event -> {
            try {
              tagMediaService.remove(t, media);
            } catch (SQLException e) {
              logger.log(Level.SEVERE, e.getMessage());
            }
          });

      wrapper.getChildren().add(button);
      children.add(wrapper);
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
          });

      wrapper.getChildren().add(label);
      wrapper.getChildren().add(button);
      children.add(wrapper);
    }
  }
}
