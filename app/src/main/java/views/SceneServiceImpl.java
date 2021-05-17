package views;

import com.google.inject.Singleton;
import java.io.IOException;
import javafx.scene.Scene;
import pij.App;
import resources.ResourceService;

@Singleton
public class SceneServiceImpl implements SceneService {
  private static Scene scene;

  public Scene load(View view, ResourceService service) throws IOException {
    var loader = new Loader<App>(App.class, view, service);
    var scene = new Scene(loader.load());

    return scene;
  }

  public Scene load(View view) throws IOException {
    var loader = new Loader<App>(App.class, view);
    var scene = new Scene(loader.load());

    return scene;
  }

  public void setRootScene(Scene scene) {
    SceneServiceImpl.scene = scene;
  }

  public void change(View view) throws IOException {
    scene.setRoot(new Loader<App>(App.class, view).load());
  }
}
