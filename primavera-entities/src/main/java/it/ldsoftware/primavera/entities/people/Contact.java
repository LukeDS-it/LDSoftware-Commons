package it.ldsoftware.primavera.entities.people;

import it.ldsoftware.primavera.entities.base.BaseEntity;
import it.ldsoftware.primavera.util.ContactType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

/**
 * Created by luca on 19/04/16.
 * defines the contact for a person
 */
@Entity
public class Contact extends BaseEntity {

    @NotNull
    @Column(nullable = false)
    private ContactType contactType;

    @NotNull
    @Column(nullable = false)
    private String contactValue;

    @ManyToOne(fetch = LAZY)
    private Person person;

    @Override
    public String toString() {
        return super.toString() + ", " + contactType + " - " + contactValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Contact contact = (Contact) o;

        if (contactType != contact.contactType) return false;
        if (!contactValue.equals(contact.contactValue)) return false;
        return person != null ? person.equals(contact.person) : contact.person == null;

    }

    @Override
    public int hashCode() {
        int result = contactType.hashCode();
        result = 31 * result + contactValue.hashCode();
        result = 31 * result + (person != null ? person.hashCode() : 0);
        return result;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public String getContactValue() {
        return contactValue;
    }

    public void setContactValue(String contactValue) {
        this.contactValue = contactValue;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Contact withContactType(ContactType value) {
        setContactType(value);
        return this;
    }

    public Contact withValue(String value) {
        setContactValue(value);
        return this;
    }
}
