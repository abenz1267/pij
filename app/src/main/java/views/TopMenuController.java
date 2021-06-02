package views;

import com.google.common.io.Files;
import entities.media.Media.DataType;
import events.NewAlbumDialog;
import events.ShowAlbumView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import resources.Resource;

public class TopMenuController extends AbstractController implements Initializable {
  @FXML Button importBtn;
  @FXML Button exportBtn;
  @FXML Button albumBtn;
  @FXML Button createAlbumBtn;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    exportBtn.getStyleClass().add("btn--disabled");
  }

  @FXML
  private void enable() {
    exportBtn.getStyleClass().remove("btn--disabled");
  }

  @FXML
  private void openFileDialog() {
    var filter =
        new ExtensionFilter(
            "Media files",
            Stream.of(DataType.values()).map(Object::toString).collect(Collectors.toList()));
    var filter2 = new ExtensionFilter("Exported ZIP", "*.zip");
    var f = new FileChooser();

    f.setTitle(this.resourceService.getString(Resource.GENERIC, "importDialog"));
    f.getExtensionFilters().add(filter);
    f.getExtensionFilters().add(filter2);

    try {
      List<File> files = f.showOpenMultipleDialog(sceneService.getWindow());

      if (files != null && !files.isEmpty()) {
        for (var file : files) {
          var ext = Files.getFileExtension(file.getName());

          if (ext.equals("zip") && files.size() > 1) {
            // TODO: SOME ERROR
            return;
          }
        }

        if (Files.getFileExtension(files.get(0).getName()).equals("zip")) {
          this.mediaService.importMediaFromExport(files.get(0));
        } else {
          this.mediaService.importMedia(files);
        }
      }
    } catch (SQLException | IOException e) {
      logger.log(Level.INFO, e.getMessage());
      var alert = new Alert(AlertType.ERROR, "Importieren fehlgeschlagen.", ButtonType.OK);
      alert.showAndWait();
    }
  }

  @FXML
  private void newAlbumDialog() {
    eventService.post(new NewAlbumDialog());

    // private void CreateAlbumDialog() {

    Dialog<NewAlbum> newAlbumDialog = new Dialog<>();
    Label AlbumNameLabel = new Label("Name:  ");
    Label AlbumThemeLabel = new Label("Thema:  ");

    TextField AlbumNameField = new TextField();
    TextField AlbumThemeField = new TextField();

    GridPane newAlbumPane = new GridPane();
    newAlbumPane.setAlignment(Pos.CENTER);
    newAlbumPane.setHgap(10);
    newAlbumPane.setVgap(10);
    newAlbumPane.setPadding(new Insets(20, 35, 20, 35));
    newAlbumPane.add(AlbumNameLabel, 1, 1);
    newAlbumPane.add(AlbumNameField, 2, 1);
    newAlbumPane.add(AlbumThemeLabel, 1, 2);
    newAlbumPane.add(AlbumThemeField, 2, 2);
    newAlbumDialog.getDialogPane().setContent(newAlbumPane);

    ButtonType okButton = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
    newAlbumDialog.getDialogPane().getButtonTypes().add(okButton);

    newAlbumDialog.setResultConverter(
        new Callback<ButtonType, NewAlbum>() {
          @Override
          public NewAlbum call(ButtonType b) {
            if (b == okButton) {
              return new NewAlbum(AlbumNameField.getText(), AlbumThemeField.getText());
            }

            return null;
          }
        });
  }

  class NewAlbum {
    private String AlbumName;
    private String AlbumTheme;

    NewAlbum(String s1, String s2) {
      AlbumName = s1;
      AlbumTheme = s2;
    }

    @Override
    public String toString() {
      return (AlbumName + ", " + AlbumTheme);
    }
  }
  // }

  @FXML
  private void showAlbumView() {
    eventService.post(new ShowAlbumView());
  }
}
