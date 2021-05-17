package views;

import com.google.inject.ImplementedBy;
import java.io.IOException;
import javafx.scene.Scene;

@ImplementedBy(SceneServiceImpl.class)
public interface SceneService {
  Scene load(View view) throws IOException;

  void change(View view) throws IOException;
}
