package views;

import com.google.common.eventbus.Subscribe;
import events.ShowAlbumView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import pij.App;

public class ContextController extends AbstractController implements Initializable {
  @FXML FlowPane wrapper;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // placeholder
  }

  @Subscribe
  public void listen(ShowAlbumView event) {
    try {
      this.wrapper.getScene().setRoot(new Loader(App.class, View.ALBUMCONTEXT, resourceService).load());
    } catch (IOException e) {
      this.logg
    }
  }
}
