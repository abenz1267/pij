package views;

import com.google.common.io.Files;
import entities.media.Media.DataType;
import events.SetUIState;
import events.SetUIState.State;
import events.ShowImages;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import resources.Resource;

/**
 * Controller to handle the top menu
 *
 * @author Andrej Benz
 * @author Timm Lohmann
 * @author Joey Wille
 * @author Phillip Knutzen
 * @author Christian Paulsen
 * @author Kelvin Leclaire
 */
public class TopMenuController extends AbstractController implements Initializable {
  @FXML Button importBtn;
  @FXML Button exportBtn;
  @FXML Button albumBtn;
  @FXML Button createAlbumBtn;
  @FXML Button imagesBtn;
  @FXML Button diashowBtn;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // empty initialize
  }

  @FXML
  private void openFileImportDialog() {
    var filter =
        new ExtensionFilter(
            "Media files",
            Stream.of(DataType.values()).map(Object::toString).collect(Collectors.toList()));
    var filter2 = new ExtensionFilter("Exported ZIP", "*.zip");
    var f = new FileChooser();

    f.setTitle(this.resourceService.getString(Resource.GENERIC, "importDialog"));
    f.getExtensionFilters().add(filter);
    f.getExtensionFilters().add(filter2);

    try {
      List<File> files = f.showOpenMultipleDialog(sceneService.getWindow());

      if (files != null && !files.isEmpty()) {
        for (var file : files) {
          var ext = Files.getFileExtension(file.getName());

          if (ext.equals("zip") && files.size() > 1) {
            var alert = new Alert(AlertType.ERROR, "Nur eine Zip zur Zeit erlaubt", ButtonType.OK);
            alert.showAndWait();
            return;
          }
        }

        if (Files.getFileExtension(files.get(0).getName()).equals("zip")) {
          this.mediaService.importMediaFromExport(files.get(0));
        } else {
          this.mediaService.importMedia(files);
        }
      }

      if (files != null) {
        eventService.post(new ShowImages(mediaService.dao().queryForAll()));
      }
    } catch (SQLException | IOException e) {
      logger.log(Level.INFO, e.getMessage());
      var alert = new Alert(AlertType.ERROR, "Importieren fehlgeschlagen.", ButtonType.OK);
      alert.showAndWait();
    }
  }

  @FXML
  private void showAlbumView() {
    eventService.post(new SetUIState(State.ALBUMLIST));
  }

  @FXML
  private void showImagesView() {
    eventService.post(new SetUIState(SetUIState.State.INITIAL));
  }

  @FXML
  private void showExport() {
    eventService.post(new SetUIState(State.EXPORT));
  }

  @FXML
  private void showDiashowPrep() {
    eventService.post(new SetUIState(State.DIASHOW));
  }
}
