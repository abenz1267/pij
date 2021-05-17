package pij;

import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class App extends Application {

  @Override
  public void start(Stage stage) throws IOException {

    var configBundle = ResourceBundle.getBundle("config");
    var ui = configBundle.getString("mainview");

    var loader = new FXMLLoader(getClass().getResource(ui));

    var scene = new Scene(loader.load());

    var title = configBundle.getString("title");

    stage.setTitle(title);
    stage.setScene(scene);
    stage.centerOnScreen();

    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
