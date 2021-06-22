package views;

import events.ShowImages;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * Controller to handle the search
 *
 * @author Huilun Chen
 */
public class SearchController extends AbstractController implements Initializable {
  @FXML TextField searchInput;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // needed for abstract
  }

  @FXML
  /**
   * OnSearch triggered by the search input textField and/or search button
   */
  public void onSearch() {
    try {
      eventService.post(new ShowImages(mediaService.filterMediaByInput(searchInput.getText())));
    } catch (SQLException e) {
      this.logger.log(Level.SEVERE, e.getMessage());
    }
  }
}
