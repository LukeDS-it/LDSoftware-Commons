package it.ldsoftware.rest.services.people;

import it.ldsoftware.primavera.entities.people.User;
import it.ldsoftware.rest.payload.people.UserPayload;
import it.ldsoftware.rest.services.base.RestService;
import org.springframework.web.bind.annotation.RestController;

@RestController("/users")
public class UserRestService extends RestService<User, UserPayload> {
    //TODO
}
