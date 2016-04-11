package it.ldsoftware.commons.util;

import it.ldsoftware.commons.dto.people.UserDTO;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by luca on 11/04/16.
 * <p>
 * This class is used to get the current user and assess if the user has
 * certain permissions.
 */
public class UserUtil {
    public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS",
            ROLE_SUPERADMIN = "ROLE_SUPERADMIN";

    /**
     * Returns the presentation object of the current user, or null if the user
     * is browsing anonymously
     *
     * @return the current user or null
     */
    public static UserDTO getCurrentUser() {
        UserDTO user = null;
        try {
            user = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception ignored) {

        }
        return user;
    }

    /**
     * Returns if the current user can access the function specified in the role
     *
     * @param role name of the role (with ROLE_ prefix)
     * @return true or false
     */
    public static boolean isCurrentUserEnabled(String role) {
        return (role.equals(ROLE_ANONYMOUS) || containsRole(role) || isCurrentUserSuperAdmin());
    }

    /**
     * Returns if the user has "super administrator" privileges,
     * meaning they can do anything.
     *
     * @return true or false
     */
    public static boolean isCurrentUserSuperAdmin() {
        return containsRole(ROLE_SUPERADMIN);
    }

    private static boolean containsRole(String role) {
        UserDTO user = getCurrentUser();
        return (user != null && (user.getAuthorities().size() > 0
                && user.getAuthorities().stream().filter(r -> r.getAuthority().equals(role)).count() > 0));
    }

}
