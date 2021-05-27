package pij;

import com.google.inject.Guice;
import entities.DatabaseConnectionService;
import fr.brouillard.oss.cssfx.CSSFX;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import resources.Resource;
import resources.ResourceService;
import views.SceneService;
import views.SceneServiceImpl;
import views.View;

public class App extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    CSSFX.start();
    var i = Guice.createInjector();
    var resourceService = i.getInstance(ResourceService.class);
    var databaseService = i.getInstance(DatabaseConnectionService.class);
    var sceneService = i.getInstance(SceneService.class);

    resourceService.setContentFiles("mediafiles", "data.db");
    databaseService.connect();

    var scene = sceneService.load(View.MAINVIEW);
    SceneServiceImpl.setRootScene(scene);

    resourceService.setStageTitle(stage, Resource.CONFIG, View.MAINVIEW.toString());
    stage.setScene(scene);
    stage.centerOnScreen();

    stage.show();
  }

  public static void main(String[] args) {
    var files = new File("mediafiles");

    if (!files.exists()) {
      files.mkdir();
    }

    launch();
  }
}
