package views;

import com.google.common.eventbus.Subscribe;
import events.ShowAlbumView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import events.ShowImage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;

public class ContextController extends AbstractController implements Initializable {
  @FXML FlowPane wrapper;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }

  @Subscribe
  public void showMetadata(ShowImage event) {
    try {
      sceneService.setContent(this.wrapper, View.METADATAVIEW);
    } catch (IOException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }
}
