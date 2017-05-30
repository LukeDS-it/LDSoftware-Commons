package it.ldsoftware.primavera.presentation.people;

import it.ldsoftware.primavera.presentation.base.BaseDTO;
import it.ldsoftware.primavera.presentation.security.GroupDTO;
import it.ldsoftware.primavera.presentation.security.RoleDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This DTO is the representation of an user that contains the information
 * that an application should know when handling actions within the authentication
 * realm, e.g. the password will not be passed along.
 *
 * @author Luca Di Stefano
 */
@Getter @Setter
public class UserVM extends BaseDTO {

    public boolean enabled;
    public String username, primaryEmail, fullName, name, surname;
    private List<RoleDTO> roles = new ArrayList<>();
    private List<GroupDTO> groups = new ArrayList<>();

}
