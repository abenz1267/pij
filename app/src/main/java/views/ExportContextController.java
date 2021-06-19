package views;

import com.google.common.eventbus.Subscribe;
import com.google.common.io.Files;
import entities.media.Media;
import events.AddToExport;
import events.SetUIState;
import events.SetUIState.State;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.kordamp.ikonli.javafx.FontIcon;
import resources.Resource;

public class ExportContextController extends AbstractController implements Initializable {
  @FXML VBox list;
  List<Media> media = new ArrayList<>();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }

  @Subscribe
  private void add(AddToExport event) {
    var children = list.getChildren();
    media.add(event.getMedia());

    var wrapper = new HBox();
    wrapper.getStyleClass().add("file");
    var label = new Label(event.getMedia().getName());
    var button = new Button();
    var icon = new FontIcon("mdi2d-delete");
    button.setGraphic(icon);

    button.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent e) {
            media =
                media.stream().filter(i -> i.equals(event.getMedia())).collect(Collectors.toList());

            Node node = null;
            for (var child : children) {
              var id = (int) child.getProperties().get("key");
              if (id == event.getMedia().getId()) {
                node = child;
              }
            }

            children.remove(node);
          }
        });

    wrapper.getChildren().add(label);
    wrapper.getChildren().add(button);

    wrapper.getProperties().put("key", event.getMedia().getId());

    children.add(wrapper);
  }

  @FXML
  private void chooseDest() {
    if (this.media.isEmpty()) {
      var alert = new Alert(AlertType.ERROR, "Keine Dateien zum export ausgewählt", ButtonType.OK);
      alert.showAndWait();
      return;
    }

    var filter = new ExtensionFilter("Exported ZIP", "*.zip");
    var f = new FileChooser();

    f.setTitle(this.resourceService.getString(Resource.GENERIC, "exportDialog"));
    f.getExtensionFilters().add(filter);

    var file = f.showSaveDialog(sceneService.getWindow());
    var ext = Files.getFileExtension(file.getName());

    if (ext == "") {
      var alert = new Alert(AlertType.ERROR, "Datei muss auf '.zip' enden", ButtonType.OK);
      alert.showAndWait();
      return;
    }

    try {
      mediaService.exportMedia(this.media, file.toPath());
    } catch (IOException | SQLException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }

  @FXML
  private void closeExport() {
    eventService.post(new SetUIState(State.CLOSE_CONTEXT));
  }
}
