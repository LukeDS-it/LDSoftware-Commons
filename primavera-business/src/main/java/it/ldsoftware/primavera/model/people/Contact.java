package it.ldsoftware.primavera.model.people;

import it.ldsoftware.primavera.model.base.BaseEntity;
import it.ldsoftware.primavera.presentation.enums.ContactType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

/**
 * Created by luca on 19/04/16.
 * defines the contact for a person
 */
@Entity
@Getter @Setter
@Table(name = "fw_contacts")
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

    public Contact withContactType(ContactType value) {
        setContactType(value);
        return this;
    }

    public Contact withValue(String value) {
        setContactValue(value);
        return this;
    }
}
