package it.ldsoftware.primavera.presentation.people;

import it.ldsoftware.primavera.presentation.security.GroupDTO;
import it.ldsoftware.primavera.presentation.security.RoleDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DTO that represents an user
 *
 * TODO must be split between the user that has to be viewed during editing and the user for front-end usage
 *
 * @author luca
 */
@Getter @Setter
public class UserDTO extends PersonDTO {

    public static final String FIELD_USERNAME = "username", FIELD_PASSWORD = "password",
            FIELD_CONFIRM_PASSWORD = "confirmPassword",
            FIELD_PRIMARY_EMAIL = "primaryEmail", FIELD_AUTHORITIES = "authorities",
            FIELD_GROUPS = "groups", FIELD_ENABLED = "enabled";

    private String username, password, primaryEmail;

    private boolean enabled;

    private List<RoleDTO> roles = new ArrayList<>();
    private List<GroupDTO> groups = new ArrayList<>();

    @Override
    public List<String> _fields() {
        List<String> tmp = super._fields();
        tmp.addAll(Arrays.asList(FIELD_USERNAME, FIELD_PRIMARY_EMAIL, FIELD_ENABLED));
        return tmp;
    }

}
