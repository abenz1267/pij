package views;

import com.google.common.eventbus.Subscribe;
import entities.album.Album;
import events.ShowAlbums;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AlbumContextController extends AbstractController implements Initializable {
  @FXML private FlowPane flowPane;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }

  @Subscribe
  public void displayAlbums(ShowAlbums event) {
    flowPane.getChildren().clear();


    }
}
