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
