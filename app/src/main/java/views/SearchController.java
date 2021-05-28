package views;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class SearchController extends AbstractController implements Initializable {
    @Inject
    Logger logger;
    @FXML
    Button searchButton;
    @FXML
    TextField searchInput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onSearch() {
        System.out.println(searchInput.getText());
    }


}
