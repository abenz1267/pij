package pij;

import entities.DatabaseConnectionService;
import entities.media.MediaService;
import fr.brouillard.oss.cssfx.CSSFX;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import resources.Resource;
import resources.ResourceService;
import views.Loader;
import views.MediaViewController;
import views.SceneService;
import views.View;

public class App extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    CSSFX.start();
    var i = Loader.getInjector();
    var resourceService = i.getInstance(ResourceService.class);
    var databaseService = i.getInstance(DatabaseConnectionService.class);
    var sceneService = i.getInstance(SceneService.class);
    var mediaViewController = i.getInstance(MediaViewController.class);


    resourceService.setContentFiles("mediafiles", "data.db");
    databaseService.createSchema(); 

    var pane = sceneService.load(View.MAINVIEW);
    //pane.getChildren().add(mediaViewController.initializeMediaView());
    var scene = new Scene(pane);
    sceneService.setRootScene(scene);


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
