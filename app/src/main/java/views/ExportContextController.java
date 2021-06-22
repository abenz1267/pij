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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import resources.Resource;

/**
 * Controller handling the export context
 *
 * @author Andrej Benz
 */
public class ExportContextController extends AbstractController implements Initializable {
  @FXML VBox list;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }

  @Subscribe
  private void add(AddToExport event) {
    this.addToList(event.getMedia(), list);
  }

  @FXML
  private void chooseDest() {
    this.chooseDest(new ArrayList<>(this.itemList));
  }

  private void chooseDest(List<Media> items) {
    if (items.isEmpty()) {
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

    if (ext.equals("")) {
      var alert = new Alert(AlertType.ERROR, "Datei muss auf '.zip' enden", ButtonType.OK);
      alert.showAndWait();
      return;
    }

    try {
      mediaService.exportMedia(items, file.toPath());
      this.closeExport();
    } catch (IOException | SQLException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }

  @FXML
  private void closeExport() {
    eventService.post(new SetUIState(State.CLOSE_CONTEXT));
  }

  @FXML
  private void exportAll() {
    try {
      this.chooseDest(mediaService.dao().queryForAll());
      this.closeExport();
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }
}
