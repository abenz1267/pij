package views;

import com.google.inject.Inject;
import entities.media.Media.DataType;
import events.TestEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import resources.Resource;

public class TopMenuController extends AbstractController implements Initializable {
  @Inject Logger logger;
  @FXML Button importBtn;
  @FXML Button exportBtn;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    exportBtn.getStyleClass().add("btn--disabled");
  }

  @FXML
  private void enable() {
    exportBtn.getStyleClass().remove("btn--disabled");
    this.eventService.post(new TestEvent("Der text kommt von einem anderen Controller"));
  }

  @FXML
  private void openFileDialog() {
    var filter =
        new ExtensionFilter(
            "Media files",
            Stream.of(DataType.values()).map(e -> e.toString()).collect(Collectors.toList()));
    var f = new FileChooser();

    f.setTitle(this.resourceService.getString(Resource.GENERIC, "importDialog"));
    f.getExtensionFilters().add(filter);

    try {
      List<File> files = f.showOpenMultipleDialog(sceneService.getWindow());

      if (files != null && files.size() > 0) {
        this.mediaService.importMedia(files);
      }
    } catch (IOException e) {
      logger.log(Level.INFO, e.getMessage());
    }
  }
}
