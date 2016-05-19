package it.ldsoftware.primavera.dto.people;

import it.ldsoftware.primavera.dto.base.BaseDTO;
import it.ldsoftware.primavera.entities.people.Contact;
import it.ldsoftware.primavera.entities.people.Person;
import it.ldsoftware.primavera.util.ContactType;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * DTO that represents a person
 *
 * @author luca
 */
public class PersonDTO extends BaseDTO<Person> {

    public final static String FIELD_NAME = "name", FIELD_SURNAME = "", FIELD_FULL_NAME = "fullName",
            FIELD_TELEPHONE = "telephone", FIELD_MOBILE = "mobile", FIELD_EMAIL = "email",
            FIELD_RECORD_TYPE = "recordType", FIELD_UNIQUE_ID = "uniqueId",
            FIELD_VAT_INFO = "vatInfo", FIELD_SEX = "sex", FIELD_BIRTH_DATE = "birthDate";

    private String fullName, telephone, mobile, email, recordType, uniqueId, vatInfo, sex;

    private Calendar birthDate;

    public PersonDTO(Person person) {
        super(person);
        fullName = person.getFullName();

        Contact ph = person.getContact(ContactType.PHONE);
        Contact mo = person.getContact(ContactType.MOBILE_PHONE);
        Contact ma = person.getContact(ContactType.EMAIL);

        telephone = (ph != null ? ph.getContactValue() : "");
        mobile = (mo != null ? mo.getContactValue() : "");
        email = (ma != null ? ma.getContactValue() : "");
        uniqueId = person.getUniqueId();
        vatInfo = person.getVatInfo();
        sex = person.getSex();
        birthDate = person.getBirthDate();

        switch (person.getPersonType()) {
            case REAL:
                recordType = "J";
                break;
            case ABSTRACT:
                recordType = "P";
                break;
        }
    }

    @Override
    public List<String> _fields() {
        List<String> tmp = super._fields();
        tmp.addAll(Arrays.asList(FIELD_FULL_NAME, FIELD_TELEPHONE, FIELD_MOBILE, FIELD_EMAIL,
                FIELD_RECORD_TYPE, FIELD_UNIQUE_ID, FIELD_VAT_INFO, FIELD_SEX, FIELD_BIRTH_DATE));
        return tmp;
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
}
