package views;

import com.google.inject.Inject;
import entities.media.Media;
import events.EventService;
import events.ShowImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class MediaViewController extends AbstractController implements Initializable {
  @FXML GridPane gridPane;
  @Inject private SceneService sceneService;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }

  @FXML
  public List<ImageView> getMediaListView(List<Media> mediaList) {
    List<ImageView> listView = new ArrayList<>();

    var thumbnailList = createThumbnails(mediaList);
    thumbnailList.forEach(
        thumbnail -> {
          ListCell listCell = new ListCell<>();
          listCell.setGraphic(thumbnail);
          listView.add(thumbnail);
        });
    return listView;
  }

  private List<ImageView> createThumbnails(List<Media> mediaList) {
    List<ImageView> resizedImages = new ArrayList<>();
    mediaList.forEach(
        media -> {
          File file = new File(media.getFilename());
          Image tempImage = new Image(file.toURI().toString());
          WritableImage newImage = null;

          /*            PixelReader reader = tempImage.getPixelReader();
            if (tempImage.getHeight() == tempImage.getWidth()) {
          } else if (tempImage.getHeight() < tempImage.getWidth()) {
            // höhe zum ausschneiden nehmen
            newImage =
                new WritableImage(
                    reader, 0, 0, (int) tempImage.getHeight(), (int) tempImage.getHeight());
          } else {
            // breite zum ausschneiden nehmen
            newImage =
                new WritableImage(
                    reader, 0, 0, (int) tempImage.getWidth(), (int) tempImage.getWidth());
          }*/

          ImageView imageView = new ImageView();
          if (newImage != null) imageView.setImage(newImage);
          else imageView.setImage(tempImage);

          imageView.setFitHeight(200);
          imageView.setPreserveRatio(true);
          imageView.setSmooth(true);
          imageView.setCache(true);

          imageView.addEventHandler(
              MouseEvent.MOUSE_CLICKED,
              event -> {
                // System.out.println("Image " + media.getFilename() + " was pressed");
                var service = Loader.getInjector().getInstance(EventService.class);
                service.post(new ShowImage(media.getId()));
                event.consume();
              });

          resizedImages.add(imageView);
        });
    return resizedImages;
  }
}
