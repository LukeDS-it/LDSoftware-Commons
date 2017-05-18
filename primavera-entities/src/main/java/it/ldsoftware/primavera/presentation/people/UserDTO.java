package it.ldsoftware.primavera.presentation.people;

import it.ldsoftware.primavera.presentation.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

/**
 * DTO that represents an user
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

    // TODO move to business in an implementation of UserDetails
//    private void parseAuthorities(User entity) {
//        Stream.concat(entity.getGroups()
//                        .stream()
//                        .flatMap(group -> group.getGroupRoles().stream())
//                        .flatMap(groupRole -> groupRole.getActualRoles().stream()),
//                entity.getUserRoles()
//                        .stream()
//                        .flatMap(userRole -> userRole.getActualRoles().stream()))
//                .map(SimpleGrantedAuthority::new)
//                .forEach(authorities::add);
//    }

    @Override
    public List<String> _fields() {
        List<String> tmp = super._fields();
        tmp.addAll(Arrays.asList(FIELD_USERNAME, FIELD_PRIMARY_EMAIL, FIELD_ENABLED));
        return tmp;
    }

}
