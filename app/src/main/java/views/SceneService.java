package views;

import com.google.inject.ImplementedBy;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

@ImplementedBy(SceneServiceImpl.class)
public interface SceneService {
  Pane load(View view) throws IOException;

  void change(View view) throws IOException;

  Window getWindow();

  void setRootScene(Scene scene);

  void setContent(Pane pane, View view) throws IOException;
}
