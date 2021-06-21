package views;

import com.google.common.eventbus.Subscribe;
import entities.media.Media;
import events.AddToAlbum;
import events.SetUIState;
import events.SetUIState.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddToAlbumViewController extends AbstractController implements Initializable {
  @FXML private FlowPane flowPane;

  private List<Media> media = new ArrayList<>();

  @FXML
  VBox list;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }

  @Subscribe
  private void add(AddToAlbum event) {
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
            e -> {
              media.remove(event.getMedia());

              Node node = null;
              for (var child : children) {
                var id = (int) child.getProperties().get("key");
                if (id == event.getMedia().getId()) {
                  node = child;
                }
              }

              children.remove(node);
            });

    wrapper.getChildren().add(label);
    wrapper.getChildren().add(button);

    wrapper.getProperties().put("key", event.getMedia().getId());

    children.add(wrapper);
  }

  @FXML
  private void closeAdd() {
    eventService.post(new SetUIState(State.CLOSE_CONTEXT));
  }

  public void addToAlbum(ActionEvent actionEvent) {

  }
}
