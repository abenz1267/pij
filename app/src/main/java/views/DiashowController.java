package views;

import com.google.common.eventbus.Subscribe;
import entities.media.Media;
import events.PlayDiashow;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class DiashowController extends AbstractController implements Initializable {
    private List<Media> media;
    private final StackPane stackPane = new StackPane();
    private Timeline timer;
    private int currentImage = 0;
    private final int slideDuration = 5;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventService.register(this);
        stackPane.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    @Subscribe
    public void playDiashow(PlayDiashow event) {
        logger.log(Level.INFO, "Diashow started");
        this.currentImage = 0;
        this.media = event.getMedia();
        this.stackPane.getChildren().clear();


        Scene secondScene = new Scene(stackPane, 230, 100);
        Stage newWindow = new Stage();
        newWindow.setFullScreen(true);
        newWindow.setTitle("Diashow");
        newWindow.setScene(secondScene);
        newWindow.show();

        stackPane.addEventHandler(KeyEvent.KEY_RELEASED, event1 -> {
            if (event1.getCode().equals(KeyCode.ESCAPE)) {
                this.logger.log(Level.INFO, "Diashow closed");
                newWindow.close();
                timer.stop();
            }
        });
        
        cycleThroughImages();
    }

    private void cycleThroughImages() {
        timer = new Timeline(
                new KeyFrame(Duration.seconds(slideDuration), event -> setCurrentImage(currentImage)));
        timer.play();
    }

    private void setCurrentImage(int index) {
        if (index >= media.size() - 1) currentImage = 0;
        stackPane.getChildren().clear();
        this.currentImage = index;
        ImageView imageView = new ImageView(new Image(media.get(index).getFilename(), stackPane.getWidth(), stackPane.getHeight(), true,true));
        stackPane.getChildren().add(imageView);
        currentImage++;
    }






}
