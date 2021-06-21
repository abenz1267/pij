package views;

import com.google.common.eventbus.Subscribe;
import entities.media.Media;
import events.AddToDiashow;
import events.AddToExport;
import events.PlayDiashow;
import events.ShowImages;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

/**
 * Controller handling the images that will be displayed in the mainwrapper.
 *
 * @author Andrej Benz
 * @author Christian Paulsen
 * @author Kelvin Leclaire
 */
public class ImagesViewController extends AbstractController implements Initializable {
  @FXML private TilePane imageWrapper;
  @FXML private ScrollPane scrollPane;
  @FXML private Pagination pagination;

  private int maxImages = 10;
  private List<Media> media;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);

    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
  }

  /**
   * The listener for the {@link ShowImages} event.
   * @param event the event to listen for
   */
  @Subscribe
  public void displayMedia(ShowImages event) {
    this.media = event.getMedia();
    display();
  }

  /**
   * Displays the images from a {@link ShowImages} event in the mainwrapper.
   */
  private void display() {
    var offset = 0;
    if ((this.media.size() % maxImages) != 0) {
      offset = 1;
    }

    pagination.setPageCount(this.media.size() / maxImages + offset);
    if ((this.media.size() / maxImages + offset) == 0) {
      pagination.setPageCount(1);
    }

    pagination.setPageFactory(
        pageIndex -> {
          imageWrapper.getChildren().clear();
          List<Media> toShow = new ArrayList<>();

          for (var i = 0; i < maxImages; i++) {
            var toGet = (pageIndex * maxImages) + i;
            if (toGet < this.media.size()) {
              toShow.add(this.media.get(toGet));
            }
          }

          var imageViews = this.createThumbnails(toShow);
          imageViews.forEach(image -> imageWrapper.getChildren().add(image));

          var label = new Label();
          label.setVisible(false);
          return label;
        });
  }

  /**
   * Creates a a list of thumbnail imageviews of the given media list.
   * @param mediaList the list, for the thumbnails.
   * @return a list of {@link ImageView} thumbnails for displaying in the mainwrapper.
   */
  private List<ImageView> createThumbnails(List<Media> mediaList) {
    List<ImageView> resizedImages = new ArrayList<>();
    mediaList.forEach(
        item -> {
          var imageView = loadImageViewFromMedia(item);
          imageView.addEventHandler(
              MouseEvent.MOUSE_CLICKED,
              event -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                  var count = event.getClickCount();
                  if (count == 2) {
                    switch (sceneService.getState()) {
                      case EXPORT:
                        eventService.post(new AddToExport(item));
                        break;
                      case DIASHOW:
                        eventService.post(new AddToDiashow(item));
                        break;
                      default:
                        break;
                    }
                    logger.log(Level.INFO, "DOUBLECLICK");
                  } else if (count == 1) {
                    logger.log(Level.INFO, "SINGLECLIKC");
                  }
                }

                event.consume();
              });

          resizedImages.add(imageView);
        });

    return resizedImages;
  }

  /**
   * Loads a Image from the given media object.
   * @param media to be converted to the image.
   * @return the ImageView of that given media object.
   */
  private ImageView loadImageViewFromMedia(Media media) {
    var file = new File(media.getFilename());
    Image tempImage;
    if (maxImages == 10) {
      tempImage = new Image(file.toURI().toString(), 400, 400, true, false);
    } else {
      tempImage =
          new Image(
              file.toURI().toString(), scrollPane.getWidth(), scrollPane.getHeight(), true, true);
    }
    var tempImageView = new ImageView();
    tempImageView.setImage(tempImage);
    tempImageView.setCache(true);
    tempImageView.setCacheHint(CacheHint.SPEED);
    return tempImageView;
  }

  /**
   * Display single image in the mainwrapper.
   */
  @FXML
  public void singleView() {
    maxImages = 1;
    display();
  }

  /**
   * Display multiple images at once in the mainwrapper.
   */
  @FXML
  public void multipleView() {
    maxImages = 10;
    display();
  }

  /**
   * Creates a new event that will start a Diashow with the media documents in the mainwrapper.
   */
  @FXML
  void playAllAsDiashow() {
    this.eventService.post(new PlayDiashow(this.media));
  }
}
