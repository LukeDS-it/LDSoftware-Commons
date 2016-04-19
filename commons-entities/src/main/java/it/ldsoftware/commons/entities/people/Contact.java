package it.ldsoftware.commons.entities.people;

import it.ldsoftware.commons.util.ContactType;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

/**
 * Created by luca on 19/04/16.
 * defines the contact for a person
 */
public class Contact {

    @NotNull
    @Column(nullable = false)
    private ContactType contactType;

    @NotNull
    @Column(nullable = false)
    private String value;

    @ManyToOne(fetch = LAZY)
    private Person person;

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
