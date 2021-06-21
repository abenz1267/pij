package views;

import com.google.common.eventbus.Subscribe;
import entities.album.Album;
import entities.albummedia.AlbumMedia;
import events.AddToAlbum;
import events.SetAlbumToAdd;
import events.SetUIState;
import events.SetUIState.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;

/**
 * Controller handling the context to add media to an album.
 *
 * @author Phillip Knutzen
 * @author Timm Lohmann
 */

public class AddToAlbumContextController extends AbstractController implements Initializable {
  @FXML VBox list;

  private Album album;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }

  @Subscribe
  private void add(AddToAlbum event) {
    this.addToList(event.getMedia(), list);
  }

  @Subscribe
  private void setAlbum(SetAlbumToAdd event) {
    this.album = event.getAlbum();
  }

  @FXML
  private void closeAdd() {
    eventService.post(new SetUIState(State.CLOSE_CONTEXT));
    eventService.post(new SetUIState(State.ALBUM, this.album));
  }

  @FXML
  public void addToAlbum(ActionEvent actionEvent) {
    itemList.forEach(item -> {
      var albumMedia = new AlbumMedia(album, item);
      try {
        albumMediaService.dao().create(albumMedia);
      } catch (SQLException e) {
        logger.log(Level.SEVERE, e.getMessage());
      }
    });

    this.closeAdd();
  }
}
