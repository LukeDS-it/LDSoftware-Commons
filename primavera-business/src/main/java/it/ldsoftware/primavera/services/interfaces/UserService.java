package it.ldsoftware.primavera.services.interfaces;

import it.ldsoftware.primavera.model.people.User;
import it.ldsoftware.primavera.presentation.people.UserDTO;

/**
 * @author Luca Di Stefano
 */
public interface UserService extends BusinessService<UserDTO> {
    User findByUsername(String username);
}
