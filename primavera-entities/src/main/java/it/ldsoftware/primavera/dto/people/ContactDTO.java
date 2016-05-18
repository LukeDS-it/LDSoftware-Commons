package it.ldsoftware.primavera.dto.people;

import it.ldsoftware.primavera.dto.base.BaseDTO;
import it.ldsoftware.primavera.entities.people.Contact;
import it.ldsoftware.primavera.util.ContactType;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by luca on 22/04/16.
 * Representation of a contact
 */
public class ContactDTO extends BaseDTO<Contact> {

    public static final String FIELD_CONTACT_TYPE = "contactType",
                                FIELD_CONTACT_VALUE = "contactValue";

    private ContactType contactType;
    private String contactValue;

    public ContactDTO(Contact entity, Locale l) {
        super(entity, l);
        contactType = entity.getContactType();
        contactValue = entity.getContactValue();
    }

    @Override
    public List<String> _fields() {
        List<String> tmp = super._fields();
        tmp.addAll(Arrays.asList(FIELD_CONTACT_TYPE, FIELD_CONTACT_VALUE));
        return tmp;
    }

    public ContactDTO(Contact contact) {
        this(contact, null);
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
}
