package it.ldsoftware.rest.payload.people;

import it.ldsoftware.rest.payload.base.BasePayload;
import it.ldsoftware.rest.payload.util.ContactType;

/**
 * Payload for a contact
 * @author luca
 */
public class ContactPayload extends BasePayload {

    private ContactType type;
    private String contact;

    public ContactType getType() {
        return type;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ContactPayload that = (ContactPayload) o;

        return type == that.type && contact.equals(that.contact);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + contact.hashCode();
        return result;
    }
}
