package views;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import resources.Resource;
import resources.ResourceService;

public class Loader extends FXMLLoader {
  private static Injector injector = Guice.createInjector();

  public Loader(Class<?> clazz, View view, ResourceService service) {
    super(
        clazz.getResource(service.getString(Resource.CONFIG, view.toString())),
        null,
        null,
        injector::getInstance);
  }
}
