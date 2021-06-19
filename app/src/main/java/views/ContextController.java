package views;

import com.google.common.eventbus.Subscribe;
import events.LoadMetaData;
import events.SetUIState;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;

public class ContextController extends AbstractController implements Initializable {
  @FXML FlowPane contextwrapper;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }

  @Subscribe
  public void setView(SetUIState event) {
    try {
      switch (event.getState()) {
        case METADATA:
          sceneService.setContent(this.contextwrapper, View.METADATAVIEW);
          eventService.post(new LoadMetaData(event.getMedia()));
          break;
        default:
          break;
      }
    } catch (IOException e) {
      this.logger.log(Level.SEVERE, e.getMessage());
    }
  }
}
