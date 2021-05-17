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

    ResourceBundle config_bundle = ResourceBundle.getBundle("config");
    String UI = config_bundle.getString("mainview");

    FXMLLoader loader = new FXMLLoader(getClass().getResource(UI));

    Scene scene = new Scene(loader.load());

    String title = config_bundle.getString("title");

    stage.setTitle(title);
    stage.setScene(scene);
    stage.centerOnScreen();

    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
