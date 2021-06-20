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
    private final int slideDuration = 3;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.logger.log(Level.INFO, this.getClass() + "initialized");
        eventService.register(this);
    }

    @Subscribe
    private  void add(AddToDiashow event) {
        if (this.media.contains(event.getMedia())) {
            return;
        }

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

    @FXML
    private void startDiashow() {
        this.eventService.post(new PlayDiashow(media));
    }
}
