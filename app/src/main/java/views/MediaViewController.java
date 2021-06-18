package views;

import entities.media.Media;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MediaViewController extends AbstractController implements Initializable {

    public int picturesPerSite = 10;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }


  public List<ImageView> createThumbnails(List<Media> mediaList, int thumbnailHeight) {
    List<ImageView> resizedImages = new ArrayList<>();
    mediaList.forEach(
        media -> {
          File file = new File(media.getFilename());
          Image tempImage = new Image(file.toURI().toString());

          ImageView imageView = new ImageView();
          imageView.setImage(tempImage);
          imageView.setFitHeight(thumbnailHeight);
          imageView.setPreserveRatio(true);
          imageView.setSmooth(true);
          imageView.setCache(true);

        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println("Image " + media.getFilename() + " was pressed");
            event.consume();
        });


          resizedImages.add(imageView);
        });
    return resizedImages;
  }
}
