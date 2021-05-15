package pij;

import com.google.inject.Guice;
import com.google.inject.Injector;
import database.DatabaseModule;
import database.DatabaseService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.inject.Inject;

public class App extends Application {
  @Inject private DatabaseService databaseService;

  @Override
  public void start(Stage stage) {
    String javaVersion = System.getProperty("java.version");
    String javafxVersion = System.getProperty("javafx.version");
    Label l =
        new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
    Scene scene = new Scene(new StackPane(l), 640, 480);

    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    Injector injector = Guice.createInjector(new DatabaseModule());

    App app = injector.getInstance(App.class);

    System.out.println(app.databaseService.hello());

    launch();
  }
}
