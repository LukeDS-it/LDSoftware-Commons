package it.ldsoftware.commons.entities.people;

import it.ldsoftware.commons.entities.base.BaseEntity;
import it.ldsoftware.commons.util.PersonType;
import it.ldsoftware.commons.validation.groups.PersonValidationGroup;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.HashSet;
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
 * @author luca
 *         <p>
 *         This class represents a person in the database.
 *         A "person" can be a real person or an abstract person, i.e. a company.
 */
@Entity
@Table(name = "people")
@Inheritance(strategy = JOINED)
public class Person extends BaseEntity {

    @NotNull(groups = PersonValidationGroup.class)
    private String name;

    @NotNull(groups = PersonValidationGroup.class)
    private String surname;

    @NotNull
    @Column(nullable = false)
    private String fullName;

    private String uniqueId;

    @Column(length = 1)
    private String sex;

    @Temporal(DATE)
    private Calendar birthDate;

    @Enumerated(STRING)
    private PersonType type;

    @ManyToOne(cascade = ALL, fetch = LAZY)
    private Person parent;

    @OneToMany(cascade = ALL, fetch = LAZY, orphanRemoval = true, mappedBy = "parent")
    private Set<Person> children = new HashSet<>();

    @OneToMany(cascade = ALL, fetch = EAGER, orphanRemoval = true, mappedBy = "person")
    private Set<Contact> contacts = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    public PersonType getType() {
        return type;
    }

    public void setType(PersonType type) {
        this.type = type;
    }

    public Person getParent() {
        return parent;
    }

    public void setParent(Person parent) {
        this.parent = parent;
    }

    public Set<Person> getChildren() {
        return children;
    }

    public void setChildren(Set<Person> children) {
        this.children = children;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + fullName;
    }
}
