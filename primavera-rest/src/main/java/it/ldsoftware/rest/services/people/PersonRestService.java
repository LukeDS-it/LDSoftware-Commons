package it.ldsoftware.rest.services.people;

import it.ldsoftware.primavera.entities.people.Person;
import it.ldsoftware.rest.payload.people.PersonPayload;
import it.ldsoftware.rest.services.base.RestService;
import org.springframework.web.bind.annotation.RestController;

@RestController("/people")
public class PersonRestService extends RestService<Person, PersonPayload> {
    // TODO
}
