package views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import java.net.URL;
import java.util.ResourceBundle;

public class MetaDataController extends AbstractController implements Initializable {
    @FXML private FlowPane flowPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventService.register(this);

        var children = flowPane.getChildren();


    }
}
