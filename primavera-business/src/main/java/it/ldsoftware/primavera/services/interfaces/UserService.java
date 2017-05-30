package it.ldsoftware.primavera.services.interfaces;

import it.ldsoftware.primavera.model.people.User;
import it.ldsoftware.primavera.presentation.people.UserDTO;
import it.ldsoftware.primavera.presentation.people.UserVM;

/**
 * @author Luca Di Stefano
 */
public interface UserService extends BusinessService<UserDTO> {
    User findByUsername(String username);

    UserVM findVMById(Long id);
}
