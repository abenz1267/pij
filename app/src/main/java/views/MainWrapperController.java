package views;

import com.google.common.eventbus.Subscribe;
import events.ShowImages;
import events.ShowImagesView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

public class MainWrapperController extends AbstractController implements Initializable {
  @FXML HBox mainwrapper;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }

  @Subscribe
  public void showPage(ShowImagesView event) {
    try {
      sceneService.setContent(this.mainwrapper, View.IMAGESVIEW);
      eventService.post(new ShowImages(mediaService.getMediaByPage(1, mediaViewController.picturesPerSite)));
    } catch (SQLException | IOException e) {
      this.logger.log(Level.SEVERE, e.getMessage());
    }
  }

}
