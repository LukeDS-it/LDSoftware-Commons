package it.ldsoftware.primavera.mapper.people;

import it.ldsoftware.primavera.mapper.base.BaseMapper;
import it.ldsoftware.primavera.model.people.Contact;
import it.ldsoftware.primavera.presentation.people.ContactDTO;
import org.springframework.stereotype.Service;

/**
 * @author Luca Di Stefano
 */
@Service
public class ContactMapper extends BaseMapper<Contact, ContactDTO> {
    @Override
    public Contact getModelInstance(ContactDTO view) {
        Contact model = new Contact();

        model.setContactType(view.getContactType());
        model.setContactValue(view.getContactValue());

        return model;
    }

    @Override
    public ContactDTO getViewInstance(Contact model) {
        ContactDTO view = new ContactDTO();

        view.setContactType(model.getContactType());
        view.setContactValue(model.getContactValue());

        return view;
    }
}
