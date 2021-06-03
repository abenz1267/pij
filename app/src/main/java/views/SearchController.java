package views;

import events.ShowFilteredImages;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController extends AbstractController implements Initializable {
    @FXML
    TextField searchInput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onSearch() {
        eventService.post(new ShowFilteredImages(searchInput.getText()));
    }
}
