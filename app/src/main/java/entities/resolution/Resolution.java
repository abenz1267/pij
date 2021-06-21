package entities.resolution;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Resolution entity
 *
 * @author Andrej Benz
 */
@DatabaseTable
public class Resolution {
  @DatabaseField(generatedId = true)
  @JsonIgnore
  private int id;

  @DatabaseField(canBeNull = false, uniqueCombo = true)
  private int width;

  @DatabaseField(uniqueCombo = true, canBeNull = false)
  private int height;

  Resolution() {}

  public Resolution(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }
}
