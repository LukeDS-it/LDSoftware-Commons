package it.ldsoftware.primavera.services;

import it.ldsoftware.primavera.dal.base.BaseDAL;
import it.ldsoftware.primavera.mapper.Mapper;
import it.ldsoftware.primavera.model.people.Person;
import it.ldsoftware.primavera.presentation.people.PersonDTO;
import it.ldsoftware.primavera.services.interfaces.BusinessService;
import it.ldsoftware.primavera.services.interfaces.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Luca Di Stefano
 */
@Service
public class PeopleBusinessService extends AbstractBusinessService<PersonDTO, Person> implements PeopleService {

    @Autowired
    public PeopleBusinessService(BaseDAL<Person> dal, Mapper<Person, PersonDTO> mapper) {
        super(dal, mapper);
    }

}
