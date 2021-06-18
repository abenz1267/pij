package views;

import com.google.common.eventbus.Subscribe;
import events.ShowImages;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.TextField;


public class ImagesViewController extends AbstractController implements Initializable {
  @FXML private FlowPane flowPane;
  @FXML private TextField textField;
  @FXML private Label label;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    eventService.register(this);
  }

  @Subscribe
  public void displayMedia(ShowImages event) {
    try {
      label.setText(" / " + String.valueOf((mediaService.getMaxRows() / mediaViewController.picturesPerSite) + 1));
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    flowPane.getChildren().clear();
    var imageViews = new MediaViewController().createThumbnails(event.getMedia(), 200);
    imageViews.forEach(image -> {
        flowPane.getChildren().add(image);
    });
  }

  public void back() {
    if (!textField.getText().equals("1")) {
      var newSite = String.valueOf(Integer.parseInt(textField.getText()) - 1);
      textField.setText(newSite);
      loadPage();
    }
  }

  public void forward() {
    try {
      if (!textField.getText().equals(String.valueOf((mediaService.getMaxRows() / mediaViewController.picturesPerSite) + 1))) {
        var newSite = String.valueOf(Integer.parseInt(textField.getText()) + 1);
        textField.setText(newSite);
        loadPage();
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  public void loadPage() {
    try {
      eventService.post(new ShowImages(mediaService.getMediaByPage(Integer.parseInt(textField.getText()), mediaViewController.picturesPerSite)));
    } catch (SQLException e) {
      this.logger.log(Level.INFO, e.getMessage());
    }
  }
}
