package views;

import com.google.common.eventbus.Subscribe;
import events.ShowImages;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

public class ImagesController extends AbstractController implements Initializable {
  @FXML private GridPane grid;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }

  @Subscribe
  public void displayMedia(ShowImages event) {
    grid.getChildren().clear();
    var imageViews = new MediaViewController().getMediaListView(event.getMedia());
    final int[] col = {0};
    final int[] row = {0};
    imageViews.forEach(
        imageView -> {
          grid.add(imageView, col[0], row[0]);
          col[0]++;
          if (col[0] == 4) {
            col[0] = 0;
            row[0]++;
          }
        });
  }
}
