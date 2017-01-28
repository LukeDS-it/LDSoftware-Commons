package it.ldsoftware.rest.mapper.people;

import it.ldsoftware.primavera.entities.people.Contact;
import it.ldsoftware.primavera.util.ContactType;
import it.ldsoftware.rest.mapper.base.BaseMapper;
import it.ldsoftware.rest.payload.people.ContactPayload;
import org.springframework.stereotype.Service;

@Service
public class ContactMapper extends BaseMapper<Contact, ContactPayload> {
    @Override
    public Contact newEntity() {
        return new Contact();
    }

    @Override
    public void initEntity(Contact entity, ContactPayload payload) {
        entity.setContactType(ContactType.valueOf(payload.getType().name()));
        entity.setContactValue(payload.getContact());
        // Not mapping the person, as it will be set from the entity's method
    }

    @Override
    public ContactPayload newPayload() {
        return new ContactPayload();
    }

    @Override
    public void initPayload(ContactPayload payload, Contact entity) {
        payload.setContact(entity.getContactValue());
        payload.setType(it.ldsoftware.rest.payload.util.ContactType.valueOf(entity.getContactType().name()));
    }
}
