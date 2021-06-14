package views;

import com.google.common.eventbus.Subscribe;
import events.ShowImages;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

public class ImagesController extends AbstractController implements Initializable {
  @FXML private FlowPane flowPane;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }

  @Subscribe
  public void displayMedia(ShowImages event) { flowPane.getChildren().clear();
    var imageViews = new MediaViewController().getMediaListView(event.getMedia());

    imageViews.forEach(image -> {
        flowPane.getChildren().add(image);
    });

  }
}
