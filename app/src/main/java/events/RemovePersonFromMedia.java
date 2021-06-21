package events;

import entities.person.Person;

public class RemovePersonFromMedia {
    private Person person;

    public RemovePersonFromMedia(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return this.person;
    }
}
