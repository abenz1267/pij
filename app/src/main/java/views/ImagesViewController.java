package views;

import com.google.common.eventbus.Subscribe;
import entities.album.Album;
import entities.media.Media;
import events.*;
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
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class ImagesViewController extends AbstractController implements Initializable {
  @FXML private TilePane imageWrapper;
  @FXML private ScrollPane scrollPane;
  @FXML private Pagination pagination;
  @FXML private Button addToAlbumBtn;
  @FXML private Label albumTitle;

  private int maxImages = 10;
  private List<Media> media;
  private Album album;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    addToAlbumBtn.setVisible(false);
    albumTitle.setText("Alle Bilder");
    eventService.register(this);

    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
  }

  @Subscribe
  public void displayMedia(ShowImages event) {
    this.media = event.getMedia();
    this.album = event.getAlbum();

    if (this.album != null) {
      addToAlbumBtn.setVisible(true);
      albumTitle.setText("Album: " + this.album.getName());
    }

    display();
  }

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
                      case ADDTOALBUM:
                        eventService.post(new AddToAlbum(item));
                        break;
                      case DIASHOW:
                        eventService.post(new AddToDiashow(item));
                        break;
                      default:
                        eventService.post(new SetUIState(SetUIState.State.METADATA, item));
                        break;
                    }
                    logger.log(Level.INFO, "DOUBLECLICK");
                  } else if (count == 1) {
                    logger.log(Level.INFO, "SINGLECLICK");
                  }
                }

                event.consume();
              });

          resizedImages.add(imageView);
        });

    return resizedImages;
  }

  private ImageView loadImageViewFromMedia(Media media) {
    var file = new File(media.getFilename());
    Image tempImage;
    if (maxImages == 10) {
      tempImage = new Image(file.toURI().toString(), 400, 400, true, false);
    } else {
      var width = media.getResolution().getWidth();
      var height = media.getResolution().getHeight();
      if (width > scrollPane.getWidth() || height > scrollPane.getHeight()) {
        width = (int) scrollPane.getWidth();
        height = (int) scrollPane.getHeight();
      }
      tempImage = new Image(file.toURI().toString(), width, height, true, true);
    }
    var tempImageView = new ImageView();
    tempImageView.setImage(tempImage);
    tempImageView.setCache(true);
    tempImageView.setCacheHint(CacheHint.SPEED);
    return tempImageView;
  }

  @FXML
  public void singleView() {
    maxImages = 1;
    display();
  }

  @FXML
  public void multipleView() {
    maxImages = 10;
    display();
  }

  @FXML
  void playAllAsDiashow() {
    this.eventService.post(new PlayDiashow(this.media));
  }

  @FXML
  private void addToAlbum() {
    eventService.post(new SetUIState(SetUIState.State.ADDTOALBUM, this.album));
  }
}
