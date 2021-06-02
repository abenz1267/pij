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
import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;

public class MediaViewController extends AbstractController implements Initializable {

    @FXML GridPane gridPane;
    @Inject private SceneService sceneService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventService.register(this);
    }

    @FXML
    public ScrollPane initializeMediaView() {
        List<Media> media;
        HBox hb = new HBox();
        gridPane = new GridPane();

        try {
            media = mediaService.dao().queryForAll();
            final int[] col = {0};
            final int[] row = {0};
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
                gridPane.add(imageView , col[0], row[0] );
                col[0]++;
                if(col[0] == 4)  {
                    col[0] = 0;
                    row[0]++;
                }

            });

            return new ScrollPane(gridPane);

        } catch (SQLException e) {
            // errorhandling
            return null;

        }

    }

}
