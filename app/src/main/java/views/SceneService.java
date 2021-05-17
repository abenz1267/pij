package views;

import com.google.inject.ImplementedBy;
import java.io.IOException;
import javafx.scene.Scene;
import resources.ResourceService;

@ImplementedBy(SceneServiceImpl.class)
public interface SceneService {
  Scene load(View view, ResourceService service) throws IOException;

  Scene load(View view) throws IOException;

  void setRootScene(Scene scene);

  void change(View view) throws IOException;
}
