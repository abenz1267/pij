package views;

import events.SetUIState;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller to handle albumview.
 *
 * @author Timm Lohmann
 * @author Phillip Knutzen
 * @author Joey Wille
 */
public class AlbumViewController extends AbstractController implements Initializable {
  @FXML private TilePane tilePane;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);

    var children = tilePane.getChildren();

    try {
      var albums = albumService.dao().queryForAll();
      for (var album : albums) {

        List<Date> dates = new ArrayList<>();

        albumMediaService.getMedia(album).forEach(media -> {
          if (media.getDatetime() != null) {
            dates.add(media.getDatetime());
          }
        });
        Optional<Date> minDate = dates.stream().min(Comparator.naturalOrder());
        Optional<Date> maxDate = dates.stream().max(Comparator.naturalOrder());
        var formatter = new SimpleDateFormat("dd-MM-yyyy");
        
        var albumBtn = new Button();
        albumBtn.getStyleClass().add("albumBtn");
        albumBtn.setMnemonicParsing(false);
        albumBtn.setText("Titel: " + album.getName() + ", Thema: " + album.getTheme());

        minDate.ifPresent(date -> albumBtn.setText(albumBtn.getText() + ", Zeitraum: "
                + formatter.format(minDate.get())
                + " bis "
                + formatter.format(maxDate.get())
        ));

        albumBtn.setOnAction(e -> eventService.post(new SetUIState(SetUIState.State.ALBUM, album)));

        children.add(albumBtn);
      }
    } catch (SQLException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }

  /**
   * Sets new stage and creates new albumdialog in scene.
   *
   * @throws SQLException if there's a problem with the database.
   */
  @FXML
  private void newAlbumDialog() throws IOException {
    var stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setScene(new Scene(sceneService.load(View.NEWALBUM)));
    stage.setTitle("Neues Album erstellen");
    stage.showAndWait();
  }
}
