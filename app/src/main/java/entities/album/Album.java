package entities.album;

import com.j256.ormlite.field.DatabaseField;

/**
 * Album entity.
 *
 * @author Timm Lohmann
 * @author Phillip Knutzen
 * @author Joey Wille
 */

public class Album {

  public static final String ID_FIELD_NAME = "id";

  @DatabaseField(generatedId = true, columnName = ID_FIELD_NAME)
  private int id;

  @DatabaseField(unique = true, canBeNull = false)
  private String name;

  @DatabaseField(canBeNull = false)
  private String theme;

  public Album(String name, String theme) {
    this.name = name;
    this.theme = theme;
  }

  public Album() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }
}
