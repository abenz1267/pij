package pij;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import resources.Resource;
import resources.ResourceModule;
import resources.ResourceService;

public class App extends Application {
  private static final String VIEW = "mainview";

  @Override
  public void start(Stage stage) throws IOException {
    Injector i = Guice.createInjector(new ResourceModule());
    var resourceService = i.getInstance(ResourceService.class);

    var loader =
        new FXMLLoader(
            getClass().getResource(resourceService.getString(Resource.CONFIG, VIEW)),
            null,
            null,
            i::getInstance);

    var scene = new Scene(loader.load());

    resourceService.setStageTitle(stage, Resource.CONFIG, VIEW);
    stage.setScene(scene);
    stage.centerOnScreen();

    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
