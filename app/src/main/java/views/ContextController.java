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

/**
 * Controller handling the context state.
 *
 * @author Andrej Benz
 * @author Timm Lohmann
 * @author Joey Wille
 * @author Phillip Knutzen
 * @author Christian Paulsen
 * @author Kelvin Leclaire
 * @author Huilun Chen
 */
public class ContextController extends AbstractController implements Initializable {
  @FXML FlowPane contextwrapper;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
    contextwrapper.setManaged(false);
    contextwrapper.setVisible(false);
  }

  @Subscribe
  public void setView(SetUIState event) {
    sceneService.setState(event.getState());

    try {
      switch (event.getState()) {
        case ALBUM:
          sceneService.setContent(this.contextwrapper, View.ALBUMCONTEXT);
          break;
        case CLOSE_CONTEXT:
          sceneService.setContent(this.contextwrapper, View.CLEAR);
          break;
        case EXPORT:
          sceneService.setContent(this.contextwrapper, View.EXPORTCONTEXT);
          break;
        case DIASHOW:
          sceneService.setContent(this.contextwrapper, View.DIASHOW);
          break;
        default:
          break;
      }
    } catch (IOException e) {
      this.logger.log(Level.SEVERE, e.getMessage());
    }
  }
}
