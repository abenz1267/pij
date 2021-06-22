package views;

import com.google.common.eventbus.Subscribe;
import events.SetUIState;
import events.ShowImages;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

/**
 * Controller handling the main wrapper
 *
 * @author Andrej Benz
 * @author Christian Paulsen
 * @author Kelvin Leclaire
 */
public class MainWrapperController extends AbstractController implements Initializable {
  @FXML HBox mainwrapper;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }

  @Subscribe
  private void showPage(SetUIState event) {
    try {
      switch (event.getState()) {
        case ALBUM:
          sceneService.setContent(this.mainwrapper, View.IMAGESVIEW);
          eventService.post(
              new ShowImages(albumMediaService.getMedia(event.getAlbum()), event.getAlbum()));
          break;
        case ALBUMLIST:
          sceneService.setContent(this.mainwrapper, View.ALBUMVIEW);
          break;
        case ALBUMCONTEXT:
          sceneService.setContent(this.mainwrapper, View.ALBUMCONTEXT);
          break;
        case INITIAL, ADDTOALBUM:
          sceneService.setContent(this.mainwrapper, View.IMAGESVIEW);
          eventService.post(new ShowImages(mediaService.dao().queryForAll()));
          break;
        default:
          break;
      }
    } catch (SQLException | IOException e) {
      this.logger.log(Level.SEVERE, e.getMessage());
    }
  }
}
