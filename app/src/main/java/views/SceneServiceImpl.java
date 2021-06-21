package views;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import events.SetUIState.State;
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
  private State state;

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

  public void setContent(Pane pane, View view) throws IOException {
    ObservableList<Node> children = pane.getChildren();
    children.clear();

    if (view.equals(View.CLEAR)) {
      return;
    }

    var nPane = this.load(view);
    children.add(nPane);
  }

  public void setState(State state) {
    this.state = state;
  }

  public State getState() {
    return this.state;
  }
}
