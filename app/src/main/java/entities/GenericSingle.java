package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DatabaseField;

public abstract class GenericSingle {
  @DatabaseField(generatedId = true)
  @JsonIgnore
  private int id;

  @DatabaseField(unique = true, canBeNull = false)
  private String name;

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
}
