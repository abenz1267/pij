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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;

public class MediaViewController extends AbstractController implements Initializable {

    @FXML ScrollPane scrollPane;
    @Inject private SceneService sceneService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        eventService.register(this);
    }

    @FXML
    public ScrollPane initializeMediaView() {
        List<Media> media;
        HBox hb = new HBox();
        scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);

        try {
            media = mediaService.dao().queryForAll();
            int i = 0;
            media.forEach(mediaFile -> {
                
                File file = new File(mediaFile.getFilename());
                Image tempImage = new Image(file.toURI().toString());
                PixelReader reader = tempImage.getPixelReader();
                WritableImage newImage = null;
                

                if(tempImage.getHeight() == tempImage.getHeight()) {

                } else if (tempImage.getHeight() < tempImage.getWidth()) {
                    //höhe zum ausschneiden nehmen
                    newImage = new WritableImage(reader, 0 , 0, (int) tempImage.getHeight(), (int) tempImage.getHeight());
                } else {
                    //breite zum ausschneiden nehmen
                    newImage = new WritableImage(reader, 0 , 0, (int) tempImage.getWidth(), (int) tempImage.getWidth());
                }

                
                ImageView imageView;
                if (newImage != null) imageView = new ImageView(newImage);
                else imageView = new ImageView(tempImage);

                imageView.setFitHeight(200);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);
                imageView.setCache(true);


                hb.getChildren().addAll(imageView);
            });

            scrollPane.setContent(hb);
            return scrollPane;

        } catch (SQLException e) {
            // errorhandling
            return null;

        }

    }

}
