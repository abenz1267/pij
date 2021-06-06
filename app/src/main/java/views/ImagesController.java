package views;

import com.google.common.eventbus.Subscribe;
import entities.media.Media;
import events.ShowAllImages;
import events.ShowFilteredImages;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class ImagesController extends AbstractController implements Initializable {
    @FXML
    private GridPane grid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventService.register(this);
    }

    @Subscribe
    public void showFilteredImages(ShowFilteredImages event) {
        this.grid.getChildren().clear();

        try {
            var items = mediaService.filterMediaByInput(event.getSearchInput());

            for (var item : items) {
                var file = new File(item.getFilename());
                var image = new Image(file.toURI().toString());
                var view = new ImageView();
                view.setImage(image);
                this.grid.getChildren().add(view);
            }
        } catch (SQLException e) {
            this.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @Subscribe
    public void showAllImages(ShowAllImages event) {
        this.grid.getChildren().clear();

        try {
            var items = mediaService.dao().queryForAll();

            for (var item : items) {
                var file = new File(item.getFilename());
                var image = new Image(file.toURI().toString());
                var view = new ImageView();
                view.setImage(image);

                this.grid.getChildren().add(view);
            }
        } catch (SQLException e) {
            this.logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
