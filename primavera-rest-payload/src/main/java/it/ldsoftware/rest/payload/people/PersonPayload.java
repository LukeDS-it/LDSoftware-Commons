package it.ldsoftware.rest.payload.people;

import it.ldsoftware.rest.payload.base.BasePayload;
import it.ldsoftware.rest.payload.util.PersonType;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Payload for a person
 * @author luca
 */
public class PersonPayload extends BasePayload {

    private String name, surname, fullName, uniqueId, vatInfo, sex;
    private Calendar birthDate;
    private PersonType type;
    private Set<PersonPayload> people = new HashSet<>();
    private Set<ContactPayload> contacts = new HashSet<>();

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

    public String getVatInfo() {
        return vatInfo;
    }

    public void setVatInfo(String vatInfo) {
        this.vatInfo = vatInfo;
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

    public Set<PersonPayload> getPeople() {
        return people;
    }

    public void setPeople(Set<PersonPayload> people) {
        this.people = people;
    }

    public Set<ContactPayload> getContacts() {
        return contacts;
    }

    public void setContacts(Set<ContactPayload> contacts) {
        this.contacts = contacts;
    }
}
