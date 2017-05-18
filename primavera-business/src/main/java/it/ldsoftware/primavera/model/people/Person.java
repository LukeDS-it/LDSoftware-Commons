package it.ldsoftware.primavera.model.people;

import it.ldsoftware.primavera.model.base.BaseEntity;
import it.ldsoftware.primavera.presentation.enums.ContactType;
import it.ldsoftware.primavera.util.PersonType;
import it.ldsoftware.primavera.validation.groups.PersonValidationGroup;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.InheritanceType.JOINED;
import static javax.persistence.TemporalType.DATE;

/**
 * Created by luca on 11/04/16.
 *
 * @author Luca Di Stefano
 *         <p>
 *         This class represents a person in the database.
 *         A "person" can be a real person or an abstract person, i.e. a company.
 */
@Entity
@Getter @Setter
@Table(name = "fw_people")
@Inheritance(strategy = JOINED)
public class Person extends BaseEntity {

    public static final String PERSON_GRAPH = "Graph.person";

    @NotNull(groups = PersonValidationGroup.class)
    private String name;

    @NotNull(groups = PersonValidationGroup.class)
    private String surname;

    @NotNull
    @Column(nullable = false)
    private String fullName;

    private String uniqueId;

    private String vatInfo;

    @Column(length = 1)
    private String sex;

    @Temporal(DATE)
    private Calendar birthDate;

    @Enumerated(STRING)
    private PersonType personType;

    @ManyToMany(cascade = ALL, fetch = LAZY)
    private Set<Person> people = new HashSet<>();

    @OneToMany(cascade = ALL, fetch = EAGER, orphanRemoval = true, mappedBy = "person")
    private Set<Contact> contacts = new HashSet<>();

    public Contact getContact(ContactType type) {
        for (Contact c : contacts)
            if (c.getContactType().equals(type))
                return c;
        return null;
    }

    public Contact getAlternativeContact(ContactType... alternatives) {
        for (ContactType a : alternatives)
            for (Contact c : contacts)
                if (c.getContactType().equals(a))
                    return c;

        return null;
    }

    public void addChildren(List<Person> people) {
        if (people != null) {
            for (Person p : people) {
                if (!p.equals(this)) {
                    this.people.add(p);
                }
            }
        }
    }

    public void remChildren(List<Person> people) {
        if (people != null) {
            for (Person p : people) {
                if (!p.equals(this)) {
                    this.people.remove(p);
                }
            }
        }
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        contact.setPerson(this);
    }

    @Override
    public String toString() {
        return super.toString() + ", " + fullName;
    }
}
