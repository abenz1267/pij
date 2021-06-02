package views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import entities.media.Media;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;

public class MediaViewController extends AbstractController implements Initializable {

    @FXML ImageView imageView;
    @FXML ScrollPane scrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventService.register(this);
    }

    @FXML
    private void initializeMediaView() {
        List<Media> media;

        try {
            media = mediaService.getAllMedia();
            media.forEach(mediaFile -> 
                System.out.println(mediaFile.getFilename())
            );

        } catch (SQLException e) {
            // errorhandling
        }


        scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);

    }

}
