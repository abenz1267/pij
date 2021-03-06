package resources;

import com.google.inject.Singleton;
import java.util.*;
import javafx.scene.control.Labeled;
import javafx.stage.Stage;

@Singleton
public class ResourceServiceImpl implements ResourceService {
  private EnumMap<Resource, ResourceBundle> bundles = new EnumMap<>(Resource.class);
  private String mediaDir;
  private String databaseFile;

  @Override
  public void setText(Labeled element, Resource bundle, String prop) {
    this.checkBundle(bundle);
    element.setText(this.bundles.get(bundle).getString(prop));
  }

  @Override
  public void setStageTitle(Stage stage, Resource bundle, String prop) {
    this.checkBundle(bundle);
    stage.setTitle(this.bundles.get(bundle).getString(prop));
  }

  @Override
  public String getString(Resource bundle, String prop) {
    this.checkBundle(bundle);

    return this.bundles.get(bundle).getString(prop);
  }

  private void checkBundle(Resource bundle) {
    if (this.bundles.containsKey(bundle)) {
      return;
    }

    this.bundles.put(bundle, ResourceBundle.getBundle(bundle.toString()));
  }

  public void setContentFiles(String mediaDir, String databaseFile) {
    this.mediaDir = mediaDir;
    this.databaseFile = databaseFile;
  }

  public String getMediaDir() {
    return mediaDir;
  }

  public String getDatabaseFile() {
    return databaseFile;
  }
}
