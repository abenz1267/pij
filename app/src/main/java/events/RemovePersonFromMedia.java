package events;

import entities.person.Person;

/**
 * Event to remove persons from media.
 *
 * @author Timm Lohmann
 * @author Phillip Knutzen
 * @author Joey Wille
 */
public class RemovePersonFromMedia {
  private Person person;

  public RemovePersonFromMedia(Person person) {
    this.person = person;
  }

  public Person getPerson() {
    return this.person;
  }
}
