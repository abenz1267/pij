package resources;

import com.google.inject.ImplementedBy;
import javafx.scene.control.Labeled;
import javafx.stage.Stage;

@ImplementedBy(ResourceServiceImpl.class)
public interface ResourceService {
  void setText(Labeled element, Resource bundle, String prop);

  void setStageTitle(Stage stage, Resource bundle, String prop);

  String getString(Resource bundle, String prop);
}
