package views;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.google.inject.Inject;

import entities.media.Media;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MediaViewController extends AbstractController implements Initializable {

    @FXML GridPane gridPane;
    @Inject private SceneService sceneService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventService.register(this);
    }

    @FXML
    public List<ImageView> getMediaListView(List<Media> mediaList) {
        List<ImageView> listView = new ArrayList<>();

        var thumbnailList = createThumbnails(mediaList);
        thumbnailList.forEach(thumbnail -> {
            ListCell listCell = new ListCell<>();
            listCell.setGraphic(thumbnail);
            listView.add(thumbnail);
        });
        return listView;
    }

    private List<ImageView> createThumbnails(List<Media> mediaList) {
        List<ImageView> resizedImages = new ArrayList<>();
        mediaList.forEach(media -> {
            File file = new File(media.getFilename());
            Image tempImage = new Image(file.toURI().toString());
            PixelReader reader = tempImage.getPixelReader();
            WritableImage newImage = null;

            if(tempImage.getHeight() == tempImage.getWidth()) {

            } else if (tempImage.getHeight() < tempImage.getWidth()) {
                //höhe zum ausschneiden nehmen
                newImage = new WritableImage(reader, 0 , 0, (int) tempImage.getHeight(), (int) tempImage.getHeight());
            } else {
                //breite zum ausschneiden nehmen
                newImage = new WritableImage(reader, 0 , 0, (int) tempImage.getWidth(), (int) tempImage.getWidth());
            }

            ImageView imageView = new ImageView();
            if (newImage != null) imageView.setImage(newImage);
            else imageView.setImage(tempImage);

            imageView.setFitHeight(200);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);
            resizedImages.add(imageView);
        });
        return resizedImages;
    }
}
