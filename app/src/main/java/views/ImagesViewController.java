package views;

import com.google.common.eventbus.Subscribe;
import entities.media.Media;
import events.ShowImages;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

public class ImagesViewController extends AbstractController implements Initializable {
  @FXML private TilePane imageWrapper;
  @FXML private ScrollPane scrollPane;
  @FXML private Pagination pagination;
  @FXML private Button singleBtn;
  @FXML private Button multipleBtn;

  private static int MAX_IMAGES = 10;
  private List<Media> media;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);

    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
  }

  @Subscribe
  public void displayMedia(ShowImages event) {
    this.media = event.getMedia();
    display();
  }

  private void display() {
    var offset = 0;
    if ((this.media.size() % MAX_IMAGES) != 0) {
      offset = 1;
    }

    pagination.setPageCount(this.media.size() / MAX_IMAGES + offset);
    if ((this.media.size() / MAX_IMAGES + offset) == 0) {
      pagination.setPageCount(1);
    }

    pagination.setPageFactory(
        (pageIndex) -> {
          imageWrapper.getChildren().clear();
          List<Media> toShow = new ArrayList<>();

          for (int i = 0; i < MAX_IMAGES; i++) {
            var toGet = (pageIndex * MAX_IMAGES) + i;
            if (toGet < this.media.size()) {
              toShow.add(this.media.get(toGet));
            }
          }

          var imageViews = this.createThumbnails(toShow);
          imageViews.forEach(
              image -> {
                imageWrapper.getChildren().add(image);
              });

          return new Label();
        });
  }

  private List<ImageView> createThumbnails(List<Media> mediaList) {
    List<ImageView> resizedImages = new ArrayList<>();

    mediaList.forEach(
        media -> {
          File file = new File(media.getFilename());
          Image tempImage = new Image(file.toURI().toString());

          ImageView imageView = new ImageView();
          imageView.setImage(tempImage);

          if (MAX_IMAGES == 10) {
            imageView.setFitWidth(400);
          }

          imageView.setPreserveRatio(true);
          imageView.setSmooth(true);
          imageView.setCache(true);

          imageView.addEventHandler(
              MouseEvent.MOUSE_CLICKED,
              event -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                  var count = event.getClickCount();
                  if (count == 2) {
                    System.out.println("DOUBLECLICK");
                  } else if (count == 1) {
                    System.out.println("Image " + media.getFilename() + " was pressed");
                  }
                }

                event.consume();
              });

          resizedImages.add(imageView);
        });

    return resizedImages;
  }

  @FXML
  public void singleView() {
    MAX_IMAGES = 1;
    display();
  }

  @FXML
  public void multipleView() {
    MAX_IMAGES = 10;
    display();
  }
}
