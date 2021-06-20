package views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class AlbumViewController extends AbstractController implements Initializable {
  @FXML
  private TilePane tilePane;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);

    var children = tilePane.getChildren();

    try {
      var albums = albumService.dao().queryForAll();
      for (var album : albums) {
        var albumBtn = new Button();
        albumBtn.setMnemonicParsing(false);
        albumBtn.setText("Titel: " + album.getName() + ", Thema: " + album.getTheme());

        children.add(albumBtn);
      }
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }
}
