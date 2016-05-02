package it.ldsoftware.commons.dto.people;

import it.ldsoftware.commons.dto.base.BaseDTO;
import it.ldsoftware.commons.entities.people.Contact;
import it.ldsoftware.commons.entities.people.Person;
import it.ldsoftware.commons.util.ContactType;

/**
 * Created by luca on 11/04/16.
 *
 * @author luca
 *         DTO that represents a person
 */
public class PersonDTO extends BaseDTO<Person> {
    private String fullName, telephone, mobile, email, recordType;

    public PersonDTO(Person person) {
        super(person);
        fullName = person.getFullName();

        Contact ph = person.getContact(ContactType.PHONE);
        Contact mo = person.getContact(ContactType.MOBILE_PHONE);
        Contact ma = person.getContact(ContactType.EMAIL);

        telephone = (ph != null ? ph.getValue() : "");
        mobile = (mo != null ? mo.getValue() : "");
        email = (ma != null ? ma.getValue() : "");
        switch (person.getPersonType()) {
            case REAL:
                recordType = "J";
                break;
            case ABSTRACT:
                recordType = "P";
                break;
        }
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }
}
