package entities.person;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Person entity.
 *
 * @author Timm Lohmann
 * @author Joey Wille
 * @author Phillip Knutzen
 */
@DatabaseTable()
public class Person {
  @DatabaseField(generatedId = true)
  @JsonIgnore
  private int id;

  @DatabaseField(canBeNull = false)
  private String firstname;

  @DatabaseField(canBeNull = false)
  private String lastname;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  @Override
  public String toString() {
    return this.firstname + " " + this.lastname;
  }
}
