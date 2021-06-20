package views;

import entities.album.Album;
import java.sql.SQLException;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewAlbumDialogController extends AbstractController {

  @FXML private TextField albumName;

  @FXML private TextField albumTheme;

  @FXML
  void btnNewAlbumClicked(ActionEvent event) {
    String name = albumName.getText().trim();
    String theme = albumTheme.getText().trim();

    try {
      albumService.createAlbum(new Album(name, theme));
      closeStage(event);
    } catch (SQLException e) {
      logger.log(Level.INFO, e.getMessage());
      var alert = new Alert(Alert.AlertType.ERROR, "Erstellen fehlgeschlagen.", ButtonType.OK);
      alert.showAndWait();
    }
  }

  private void closeStage(ActionEvent event) {
    Node source = (Node) event.getSource();
    var stage = (Stage) source.getScene().getWindow();
    stage.close();
  }
}
