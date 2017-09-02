package it.ldsoftware.primavera.services;

import it.ldsoftware.primavera.dal.base.BaseDAL;
import it.ldsoftware.primavera.mapper.Mapper;
import it.ldsoftware.primavera.model.people.Contact;
import it.ldsoftware.primavera.presentation.people.ContactDTO;
import it.ldsoftware.primavera.services.interfaces.BusinessService;
import it.ldsoftware.primavera.services.interfaces.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Luca Di Stefano
 */
@Service
public class ContactBusinessService extends AbstractBusinessService<ContactDTO, Contact> implements ContactService {

    @Autowired
    public ContactBusinessService(BaseDAL<Contact> dal, Mapper<Contact, ContactDTO> mapper) {
        super(dal, mapper);
    }

}
