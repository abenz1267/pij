package views;

import com.google.common.eventbus.Subscribe;
import entities.media.Media;
import events.PlayDiashow;
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

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class DiashowController extends AbstractController implements Initializable {
    private List<Media> media = new ArrayList<>();
    private StackPane stackPane = new StackPane();
    private int currentImage = 0;
    private static final int SLIDEDURATION = 5;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventService.register(this);
    }

    @Subscribe
    public void playDiashow(PlayDiashow event) {
        this.media = event.getMedia()
                .stream()
                .filter(item -> !item.isPrivate())
                .collect(Collectors.toList());

        logger.log(Level.INFO, "Diashow started");
        stackPane = new StackPane();
        stackPane.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        this.currentImage = 0;
        this.stackPane.getChildren().clear();
        var timer= new Timeline(
                new KeyFrame(Duration.seconds(SLIDEDURATION), event1 -> setCurrentImage(currentImage + 1)));

        var secondScene = new Scene(stackPane, 230, 100);
        var newWindow = new Stage();
        newWindow.setFullScreen(true);
        newWindow.setTitle("Diashow");
        newWindow.setScene(secondScene);
        newWindow.show();

        newWindow.addEventHandler(KeyEvent.KEY_RELEASED, event2 -> {
            switch(event2.getCode()) {
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

    private void setCurrentImage(int index) {
        if (index > media.size() - 1) currentImage = 0;
        else if (index < 0) currentImage = media.size() -1;
        else currentImage = index;

        stackPane.getChildren().clear();
        var file = new File(media.get(currentImage).getFilename());
        var tempImage = new Image(file.toURI().toString(), stackPane.getWidth(), stackPane.getHeight(), true,true);
        var imageView = new ImageView(tempImage);
        stackPane.getChildren().add(imageView);
    }
}