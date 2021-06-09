package views;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Window;
import pij.App;
import resources.ResourceService;

@Singleton
public class SceneServiceImpl implements SceneService {
  @Inject private ResourceService resourceService;

  private Scene scene;

  public Pane load(View view) throws IOException {
    var loader = new Loader(App.class, view, resourceService);
    return loader.load();
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

  public void setContent(Pane pane, View view) throws IOException {
    var nPane = this.load(view);
    ObservableList<Node> children = pane.getChildren();
    children.clear();
    children.add(nPane);
  }
}
