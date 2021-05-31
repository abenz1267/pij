package views;

import com.google.inject.ImplementedBy;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.stage.Window;

@ImplementedBy(SceneServiceImpl.class)
public interface SceneService {
  Scene load(View view) throws IOException;

  void change(View view) throws IOException;

  Window getWindow();

  void setRootScene(Scene scene);
}
