package views;

import com.google.common.eventbus.Subscribe;
import events.TestEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ContainerController extends AbstractController implements Initializable {
  @FXML Label label;

  @Subscribe
  public void listen(TestEvent event) {
    System.out.println("TEST");
    label.setText(event.getMessage());
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.eventService.register(this);
  }
}
