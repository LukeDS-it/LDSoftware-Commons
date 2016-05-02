package it.ldsoftware.commons.dto.people;

import it.ldsoftware.commons.dto.base.BaseDTO;
import it.ldsoftware.commons.entities.people.Contact;
import it.ldsoftware.commons.util.ContactType;

import java.util.Locale;

/**
 * Created by luca on 22/04/16.
 * Representation of a contact
 */
public class ContactDTO extends BaseDTO<Contact> {
    private ContactType contactType;
    private String value;

    public ContactDTO(Contact entity, Locale l) {
        super(entity, l);
        contactType = entity.getContactType();
        value = entity.getValue();
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
