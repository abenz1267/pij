package entities.personmedia;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import entities.media.Media;
import entities.person.Person;

/**
 * PersonMedia entity.
 *
 * @author Andrej Benz
 * @author Timm Lohmann
 * @author Joey Wille
 * @author Phillip Knutzen
 */
@DatabaseTable()
public class PersonMedia {

  @DatabaseField(generatedId = true)
  private int id;

  @DatabaseField(foreign = true, columnName = "person_id", uniqueCombo = true)
  Person person;

  @DatabaseField(foreign = true, columnName = "media_id", uniqueCombo = true)
  Media media;

  PersonMedia() {}

  public PersonMedia(Person person, Media media) {
    this.person = person;
    this.media = media;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public Media getMedia() {
    return media;
  }

  public void setMedia(Media media) {
    this.media = media;
  }
}
