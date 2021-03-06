package views;

import com.google.common.eventbus.Subscribe;
import entities.media.Media;
import events.PlayDiashow;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.stream.Collectors;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller handling the Diashow
 *
 * @author Christian Paulsen
 */
public class DiashowController extends AbstractController implements Initializable {
  private List<Media> media = new ArrayList<>();
  private StackPane stackPane = new StackPane();
  private int currentImage = 0;
  private static final int SLIDEDURATION = 5;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }

  /**
   * Starts a diashow in fullscreen. It takes a list of {@link Media} objects from the {@link
   * PlayDiashow} event. The media will be filtered and added to a new stage in fullscreen mode. The
   * diashow can be controlled with the left and right arrow-keys. If you skip manually through the
   * image, the diashow animation will be paused. You can resume the animation by pressing spacebar.
   * The Escape key closes the diashow.
   *
   * @param event the event that triggers the diashow.
   */
  @Subscribe
  private void playDiashow(PlayDiashow event) {
    this.media =
        event.getMedia().stream().filter(item -> !item.isPrivate()).collect(Collectors.toList());

    logger.log(Level.INFO, "Diashow started");
    stackPane = new StackPane();
    stackPane.setBackground(
        new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
    this.currentImage = 0;
    this.stackPane.getChildren().clear();
    var timer =
        new Timeline(
            new KeyFrame(
                Duration.seconds(SLIDEDURATION), event1 -> setCurrentImage(currentImage + 1)));

    var secondScene = new Scene(stackPane, 230, 100);
    var newWindow = new Stage();
    newWindow.setFullScreen(true);
    newWindow.setTitle("Diashow");
    newWindow.setScene(secondScene);
    newWindow.show();

    newWindow.addEventHandler(
        KeyEvent.KEY_RELEASED,
        event2 -> {
          switch (event2.getCode()) {
            case ESCAPE:
              this.logger.log(Level.INFO, "Diashow closed");
              newWindow.close();
              timer.stop();
              break;
            case RIGHT:
              timer.pause();
              setCurrentImage(currentImage + 1);
              break;
            case LEFT:
              timer.pause();
              setCurrentImage(currentImage - 1);
              break;
            case SPACE:
              timer.play();
              break;
            default:
              break;
          }
          event2.consume();
        });

    timer.setCycleCount(Animation.INDEFINITE);
    timer.play();
  }

  /**
   * This functions sets a new Image in the diashow window.
   *
   * @param index the index of the image to be displayed from the media list.
   */
  private void setCurrentImage(int index) {
    if (index > media.size() - 1) currentImage = 0;
    else if (index < 0) currentImage = media.size() - 1;
    else currentImage = index;

    stackPane.getChildren().clear();
    mediaService.refreshAll(media.get(currentImage));

    var file = new File(media.get(currentImage).getFilename());
    var width = media.get(currentImage).getResolution().getWidth();
    var height = media.get(currentImage).getResolution().getHeight();

    if (width > stackPane.getWidth() || height > stackPane.getHeight()) {
      width = (int) stackPane.getWidth();
      height = (int) stackPane.getHeight();
    }

    var tempImage = new Image(file.toURI().toString(), width, height, true, true);
    var imageView = new ImageView(tempImage);
    stackPane.setBackground(
        new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    stackPane.getChildren().add(imageView);
  }
}
