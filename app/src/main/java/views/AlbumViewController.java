package views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class AlbumViewController extends AbstractController implements Initializable {
  @FXML
  private FlowPane flowPane;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);

    var children = flowPane.getChildren();

    try {
      var albums = albumService.dao().queryForAll();
      for (var album : albums) {
        var albumBtn = new Button();
        albumBtn.setText(album.getName() + " - " + album.getTheme());

        children.add(albumBtn);
      }
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }
}
