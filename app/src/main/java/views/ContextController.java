package views;

import com.google.common.eventbus.Subscribe;
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
    sceneService.setState(event.getState());

    try {
      switch (event.getState()) {
        case ALBUM:
          sceneService.setContent(this.contextwrapper, View.ALBUMCONTEXT);
          break;
        case INITIAL:
          break;
        case CLOSE_CONTEXT:
          sceneService.setContent(this.contextwrapper, View.CLEAR);
          break;
        case EXPORT:
          sceneService.setContent(this.contextwrapper, View.EXPORTCONTEXT);
          break;
        default:
          break;
      }
    } catch (IOException e) {
      this.logger.log(Level.SEVERE, e.getMessage());
    }
  }
}
