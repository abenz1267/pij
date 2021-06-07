package views;

import events.ShowImages;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class SearchController extends AbstractController implements Initializable {
  @FXML TextField searchInput;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  @FXML
  public void onSearch() {
    try {
      eventService.post(new ShowImages(mediaService.filterMediaByInput(searchInput.getText())));
    } catch (SQLException e) {
      this.logger.log(Level.INFO, e.getMessage());
    }
  }
}
