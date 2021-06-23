package views;

import com.google.common.eventbus.Subscribe;
import entities.location.Location;
import entities.media.Media;
import entities.person.Person;
import entities.tag.Tag;
import events.LoadMetaData;
import events.SetUIState;
import events.SetUIState.State;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Controller to show, save and handle metadata.
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
  @FXML private ComboBox<String> tagBox;
  @FXML private FlowPane tagPane;
  @FXML private ComboBox<String> quality;


  private Media media;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }

  private void setTags(List<Tag> tags) {
    try {
      tagBox.getItems().clear();
      tagBox.setEditable(true);

      var allTags = tagService.dao().queryForAll();
      var set = new HashSet<String>();
      allTags.forEach(tag -> set.add(tag.getName()));

      tags.forEach(tag -> set.remove(tag.getName()));

      set.forEach(entry -> tagBox.getItems().add(entry));
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }

  @Subscribe
  public void getMetaData(LoadMetaData event) {
    var ev = event.getMedia();
    mediaService.refreshAll(ev);
    this.media = ev;

    if (this.media.getDatetime() != null) {
      var date = this.media.getDatetime();
      var localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      datetimePicker.setValue(localDate);
    }

    if (media.getLocation() != null) {
      locationField.setText(this.media.getLocation().toString());
      locationField.setAlignment(Pos.CENTER_RIGHT);
    }

    quality.setValue(String.valueOf(media.getQuality()));
    quality.getItems().setAll("1", "2", "3" , "4", "5");

    nameField.setText(this.media.getName());
    nameField.setAlignment(Pos.CENTER_RIGHT);

    descriptionField.setText(this.media.getDescription());
    descriptionField.setAlignment(Pos.CENTER_RIGHT);

    datatypeField.setText(this.media.getDataType().toString());
    datatypeField.setAlignment(Pos.CENTER_RIGHT);

    resolutionField.setText(this.media.getResolution().toString());
    resolutionField.setAlignment(Pos.CENTER_RIGHT);

    isPrivateBox.setSelected(this.media.isPrivate());
    isPrivateBox.setAlignment(Pos.CENTER_RIGHT);

    this.listPersons(this.media);
    this.listTags(this.media);
    this.setTags(this.media.getTags());
  }

  @FXML
  public void btnSaveDataClicked(ActionEvent event) {
    this.media.setName(nameField.getText());
    this.media.setDescription(descriptionField.getText());
    this.media.setPrivate(isPrivateBox.isSelected());
    this.media.setQuality(Integer.parseInt(quality.getValue()));

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
    eventService.post(new SetUIState(SetUIState.State.CLOSE_CONTEXT));
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
    tag.setName(tagBox.getValue());
    media.getTags().add(tag);

    try {
      mediaService.update(media);
      mediaService.refreshAll(media);

      this.listTags(media);
      this.setTags(media.getTags());
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
              this.listTags(media);
              this.setTags(media.getTags());
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

  @FXML
  private void closeMetaData() {
    eventService.post(new SetUIState(State.CLOSE_CONTEXT));
  }
}
