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
            var items = mediaService.dao().queryForAll();

            for (var item : filterMediaList(items, event.getSearchInput())) {
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

    private List<Media> filterMediaList(List<Media> mediaList, String input){
        var filteredMediaList = new ArrayList<Media>();
        var dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        mediaList.forEach(media -> {
            if ((media.getName() != null && media.getName().contains(input)) ||
                    (media.getDatetime() != null && dateFormat.format(media.getDatetime()).contains(input)) ||
                    (media.getFilename() != null && media.getFilename().contains(input)) ||
                    (media.getDescription() != null && media.getDescription().contains(input)) ||
                    (media.getLocation() != null && media.getLocation().getName().contains(input)) ||
                    (media.getPersons() != null && Arrays.stream(media.getPersons())
                            .anyMatch(person -> person.getFirstname().equals(input) || person.getLastname().equals(input))) ||
                    (media.getTags() != null && Arrays.stream(media.getTags()).anyMatch(tag -> tag.getName().equals(input))))
                filteredMediaList.add(media);
        });
        return filteredMediaList;
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
