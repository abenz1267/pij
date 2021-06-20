package views;

import com.google.common.eventbus.Subscribe;
import entities.media.Media;
import events.AddToDiashow;
import events.PlayDiashow;
import events.SetUIState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class DiashowContextController extends AbstractController implements Initializable {
    private List<Media> media = new ArrayList<Media>();
    @FXML
    VBox list;

    private StackPane stackPane = new StackPane();
    private Timeline timer;
    private int currentImage = 0;
    private final int slideDuration = 1;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventService.register(this);
    }

    @Subscribe
    private  void add(AddToDiashow event) {
        if (this.media.contains(event.getMedia())) {
            return;
        }
        this.media.add(event.getMedia());

        var children = list.getChildren();
        media.add(event.getMedia());

        var wrapper = new HBox();
        wrapper.getStyleClass().add("file");
        var label = new Label(event.getMedia().getName());
        var button = new Button();
        var icon = new FontIcon("mdi2d-delete");
        button.setGraphic(icon);

        button.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        media.remove(event.getMedia());

                        Node node = null;
                        for (var child : children) {
                            var id = (int) child.getProperties().get("key");
                            if (id == event.getMedia().getId()) {
                                node = child;
                            }
                        }

                        children.remove(node);
                    }
                });

        wrapper.getChildren().add(label);
        wrapper.getChildren().add(button);

        wrapper.getProperties().put("key", event.getMedia().getId());

        children.add(wrapper);
    }



    @FXML
    private void closeDiashowContext() {
        eventService.post(new SetUIState(SetUIState.State.CLOSE_CONTEXT));
    }


    /*
Vorher in eigener View, konnte aber kein event hin posten (Immernoch in DiashowController.. falls es ich etwas übersehen habe
 */
    @FXML
    private void startDiashow() {
        logger.log(Level.INFO, "Diashow started");
        stackPane = new StackPane();
        stackPane.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        this.currentImage = 0;
        this.stackPane.getChildren().clear();

        Scene secondScene = new Scene(stackPane, 230, 100);
        Stage newWindow = new Stage();
        newWindow.setFullScreen(true);
        newWindow.setTitle("Diashow");
        newWindow.setScene(secondScene);
        newWindow.show();

        newWindow.addEventHandler(KeyEvent.KEY_RELEASED, event1 -> {
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
                new KeyFrame(Duration.seconds(slideDuration), event -> {
                    setCurrentImage(currentImage);
                }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void setCurrentImage(int index) {
        if (index > media.size() - 1) currentImage = 0;
        stackPane.getChildren().clear();
        var file = new File(media.get(currentImage++).getFilename());
        Image tempImage = new Image(file.toURI().toString(), stackPane.getWidth(), stackPane.getHeight(), true,true);
        ImageView imageView = new ImageView(tempImage);
        stackPane.getChildren().add(imageView);
    }
}
