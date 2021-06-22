package views;

import com.google.inject.Inject;
import entities.PersonMedia.PersonMediaService;
import entities.TagMedia.TagMediaService;
import entities.album.AlbumService;
import entities.albummedia.AlbumMediaService;
import entities.media.Media;
import entities.media.MediaService;
import entities.resolution.ResolutionService;
import entities.tag.TagService;
import events.EventService;
import java.util.HashSet;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import resources.ResourceService;

/**
 * AbstractController to reduce code duplication.
 *
 * @author Andrej Benz.
 */
abstract class AbstractController {
  @Inject protected ResourceService resourceService;
  @Inject protected SceneService sceneService;
  @Inject protected EventService eventService;
  @Inject protected MediaService mediaService;
  @Inject protected ResolutionService resolutionService;
  @Inject protected Logger logger;
  @Inject protected AlbumService albumService;
  @Inject protected AlbumMediaService albumMediaService;
  @Inject protected ResolutionService resolutionService;
  @Inject protected PersonMediaService personMediaService;
  @Inject protected TagService tagService;
  @Inject protected TagMediaService tagMediaService;

  protected HashSet<Media> itemList = new HashSet<>();

  protected void addToList(Media media, VBox list) {
    if (this.itemList.contains(media)) {
      return;
    }

    var children = list.getChildren();
    itemList.add(media);

    var wrapper = new HBox();
    wrapper.getStyleClass().add("file");
    var label = new Label(media.getName());
    var button = new Button();
    var icon = new FontIcon("mdi2d-delete");
    button.setGraphic(icon);

    button.setOnAction(
        e -> {
          itemList.remove(media);

          Node node = null;
          for (var child : children) {
            var id = (int) child.getProperties().get("key");
            if (id == media.getId()) {
              node = child;
            }
          }

          children.remove(node);
        });

    wrapper.getChildren().add(label);
    wrapper.getChildren().add(button);

    wrapper.getProperties().put("key", media.getId());

    children.add(wrapper);
  }
}
