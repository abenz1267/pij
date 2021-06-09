package views;

import com.google.common.eventbus.Subscribe;
import events.ShowImages;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ImagesController extends AbstractController implements Initializable {
  @FXML private GridPane grid;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }

  @Subscribe
  public void displayMedia(ShowImages event) {
    this.grid.getChildren().clear();

    for (var item : event.getMedia()) {
      var file = new File(item.getFilename());
      var image = new Image(file.toURI().toString());
      var view = new ImageView();
      view.setImage(image);

      this.grid.getChildren().add(view);
    }
  }
}
