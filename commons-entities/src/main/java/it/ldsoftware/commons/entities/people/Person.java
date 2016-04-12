package it.ldsoftware.commons.entities.people;

import it.ldsoftware.commons.entities.base.BaseEntity;

import javax.persistence.Entity;

/**
 * Created by luca on 11/04/16.
 *
 * @author luca
 *         <p>
 *         This class represents a person in the database.
 *         A "person" can be a real person or an abstract person, i.e. a company.
 */
@Entity
public class Person extends BaseEntity {


    private String name;

    private String surname;

    private String fullName;

    private String uniqueId;

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

    @Override
    public String toString() {
        return super.toString() + ", " + fullName;
    }
}
