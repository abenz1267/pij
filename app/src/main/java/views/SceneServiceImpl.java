package views;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.stage.Window;
import pij.App;
import resources.ResourceService;

@Singleton
public class SceneServiceImpl implements SceneService {
  @Inject private ResourceService resourceService;

  private Scene scene;

  public Scene load(View view) throws IOException {
    var loader = new Loader(App.class, view, resourceService);
    return new Scene(loader.load());
  }

  public void setRootScene(Scene scene) {
    this.scene = scene;
  }

  public Window getWindow() {
    return this.scene.getWindow();
  }

  public void change(View view) throws IOException {
    scene.setRoot(new Loader(App.class, view, resourceService).load());
  }
}
