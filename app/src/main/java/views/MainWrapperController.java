package views;

import com.google.common.eventbus.Subscribe;
import events.ShowAllImages;
import events.ShowImagesView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;

public class MainWrapperController extends AbstractController implements Initializable {
  @FXML FlowPane mainwrapper;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }

  @Subscribe
  public void showAllImages(ShowImagesView event) {
    try {
      sceneService.setContent(this.mainwrapper, View.IMAGESVIEW);
      eventService.post(new ShowAllImages());
    } catch (IOException e) {
      this.logger.log(Level.SEVERE, e.getMessage());
    }
  }
}
