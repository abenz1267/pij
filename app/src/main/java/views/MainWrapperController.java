package views;

import com.google.common.eventbus.Subscribe;
import events.ShowAlbumView;
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
  public void showAllImages(ShowImagesView event) {
    try {
      sceneService.setContent(this.mainwrapper, View.IMAGESVIEW);
      eventService.post(new ShowImages(mediaService.dao().queryForAll()));
    } catch (SQLException | IOException e) {
      this.logger.log(Level.SEVERE, e.getMessage());
    }
  }

  @Subscribe
  public void showAlbums(ShowAlbumView event) {
    try {
      sceneService.setContent(this.mainwrapper, View.ALBUMVIEW);
    } catch (IOException e) {
      this.logger.log(Level.SEVERE, e.getMessage());
    }
  }
}
