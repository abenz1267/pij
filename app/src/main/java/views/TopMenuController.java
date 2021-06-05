package views;

import com.google.common.io.Files;
import entities.media.Media.DataType;
import events.ShowAlbumView;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import resources.Resource;

public class TopMenuController extends AbstractController implements Initializable {
  @FXML Button importBtn;
  @FXML Button exportBtn;
  @FXML Button albumBtn;
  @FXML Button createAlbumBtn;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    exportBtn.getStyleClass().add("btn--disabled");
  }

  @FXML
  private void enable() {
    exportBtn.getStyleClass().remove("btn--disabled");
  }

  @FXML
  private void openFileDialog() {
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
            // TODO: SOME ERROR
            return;
          }
        }

        if (Files.getFileExtension(files.get(0).getName()).equals("zip")) {
          this.mediaService.importMediaFromExport(files.get(0));
        } else {
          this.mediaService.importMedia(files);
        }
      }
    } catch (SQLException | IOException e) {
      logger.log(Level.INFO, e.getMessage());
      var alert = new Alert(AlertType.ERROR, "Importieren fehlgeschlagen.", ButtonType.OK);
      alert.showAndWait();
    }
  }

  @FXML
  private void newAlbumDialog() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layouts/newalbumdialog.fxml"));
    Parent parent = fxmlLoader.load();

    Scene scene = new Scene(parent, 300, 200);
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setScene(scene);
    stage.setTitle("Neues ALbum erstellen");
    stage.showAndWait();
  }

  @FXML
  private void showAlbumView() {
    eventService.post(new ShowAlbumView());
  }
}
