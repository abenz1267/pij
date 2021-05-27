package entities.album;

import com.j256.ormlite.field.DatabaseField;

import java.io.File;
import java.util.List;

public class Album {

  public static final String ID_FIELD_NAME = "id";

  @DatabaseField(generatedId = true, columnName = ID_FIELD_NAME)
  private int id;

  @DatabaseField List<File> contents;

  public Album(List<File> contents) {
    this.contents = contents;
  }
}
