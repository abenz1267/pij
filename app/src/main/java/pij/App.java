package pij;

import com.google.inject.Guice;
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
    var i = Guice.createInjector();
    var resourceService = i.getInstance(ResourceService.class);
    var sceneService = i.getInstance(SceneService.class);

    var scene = sceneService.load(View.MAINVIEW);
    SceneServiceImpl.setRootScene(scene);

    resourceService.setStageTitle(stage, Resource.CONFIG, View.MAINVIEW.toString());
    stage.setScene(scene);
    stage.centerOnScreen();

    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
