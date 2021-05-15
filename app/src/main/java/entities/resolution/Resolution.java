package entities.resolution;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Resolution {
  @DatabaseField(generatedId = true)
  private int id;

  @DatabaseField(canBeNull = false, uniqueCombo = true)
  private int width;

  @DatabaseField(uniqueCombo = true, canBeNull = false)
  private int height;

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
