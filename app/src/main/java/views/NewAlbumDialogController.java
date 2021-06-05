package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewAlbumDialogController {

    @FXML
    private TextField albumName;

    @FXML
    private TextField albumTheme;

    @FXML
    void btnNewAlbumClicked(ActionEvent event) {
    System.out.println("Button newAlbum clicked");
    String albumNameText = albumName.getText().trim();
    String albumThemeText = albumTheme.getText().trim();

    closeStage(event);
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
