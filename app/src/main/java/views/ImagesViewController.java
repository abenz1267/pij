package views;

import com.google.common.eventbus.Subscribe;
import entities.album.Album;
import entities.media.Media;
import events.*;
import events.AddToDiashow;
import events.AddToExport;
import events.PlayDiashow;
import events.ShowImages;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import resources.Resource;

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
  @FXML private Button singleBtn;
  @FXML private Button multiBtn;
  @FXML private Button addToAlbumBtn;
  @FXML private Label albumTitle;

  private Album album;
  private int maxImages = 10;
  private List<Media> media;
  private static final String ACTIVE = "active";

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    addToAlbumBtn.setVisible(false);
    albumTitle.setText("Alle Bilder");
    eventService.register(this);

    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);

    multiBtn.getStyleClass().add(ACTIVE);
  }

  /**
   * The listener for the {@link ShowImages} event.
   *
   * @param event the event to listen for
   */
  @Subscribe
  private void displayMedia(ShowImages event) {
    this.media = event.getMedia();
    this.album = event.getAlbum();

    if (this.album != null) {
      addToAlbumBtn.setVisible(true);
      albumTitle.setText("Album: " + this.album.getName());
    }

    display();
  }

  /** Displays the images from a {@link ShowImages} event in the mainwrapper. */
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
   *
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
                } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                  this.createContextMenu(imageView, item, media);
                }

                event.consume();
              });

          resizedImages.add(imageView);
        });

    return resizedImages;
  }

  // creates the right-click context menu to delete an image
  private void createContextMenu(ImageView view, Media media, List<Media> list) {
    var contextMenu = new ContextMenu();
    var menuItem1 = new MenuItem(resourceService.getString(Resource.GENERIC, "delete"));
    menuItem1.setOnAction(
        e -> {
          try {
            mediaService.delete(media);
            list.remove(media);
            this.display();
          } catch (SQLException | IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
          }
        });

    contextMenu.getItems().add(menuItem1);

    view.setOnContextMenuRequested(e -> contextMenu.show(view, e.getScreenX(), e.getScreenY()));
  }

  // creates the actual thumbnail ImageViews
  private ImageView loadImageViewFromMedia(Media media) {
    var file = new File(media.getFilename());
    Image tempImage;
    mediaService.refreshAll(media);
    var width = media.getResolution().getWidth();
    var height = media.getResolution().getHeight();

    if (maxImages == 10) {
      if (width > 400 || height > 400) {
        width = 400;
      }

      tempImage = new Image(file.toURI().toString(), width, height, true, false);
    } else {
      if (width > scrollPane.getWidth() || height > scrollPane.getHeight()) {
        width = (int) scrollPane.getWidth();
      }

      tempImage = new Image(file.toURI().toString(), width, height, true, true);
    }
    var tempImageView = new ImageView();
    tempImageView.setImage(tempImage);
    tempImageView.setCache(true);
    tempImageView.setCacheHint(CacheHint.SPEED);
    return tempImageView;
  }

  /** Display single image in the mainwrapper. */
  @FXML
  private void singleView() {
    maxImages = 1;
    multiBtn.getStyleClass().remove(ACTIVE);
    singleBtn.getStyleClass().add(ACTIVE);
    display();
  }

  /** Display multiple images at once in the mainwrapper. */
  @FXML
  private void multipleView() {
    maxImages = 10;
    multiBtn.getStyleClass().add(ACTIVE);
    singleBtn.getStyleClass().remove(ACTIVE);
    display();
  }

  /** Creates a new event that will start a Diashow with the media documents in the mainwrapper. */
  @FXML
  private void playAllAsDiashow() {
    this.eventService.post(new PlayDiashow(this.media));
  }

  @FXML
  private void addToAlbum() {
    eventService.post(new SetUIState(SetUIState.State.ADDTOALBUM, this.album));
  }
}
