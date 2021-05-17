package views;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.io.IOException;
import javafx.scene.Scene;
import pij.App;
import resources.ResourceService;

@Singleton
public class SceneServiceImpl implements SceneService {
  @Inject private ResourceService resourceService;

  private static Scene scene;

  public Scene load(View view) throws IOException {
    var loader = new Loader(App.class, view, resourceService);
    return new Scene(loader.load());
  }

  public static void setRootScene(Scene scene) {
    SceneServiceImpl.scene = scene;
  }

  public void change(View view) throws IOException {
    scene.setRoot(new Loader(App.class, view, resourceService).load());
  }
}
