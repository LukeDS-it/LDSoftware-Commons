package it.ldsoftware.primavera.util;

import it.ldsoftware.primavera.model.security.Role;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by luca on 11/04/16.
 * <p>
 * This class is used to get the current user and assess if the user has
 * certain permissions.
 */
public class UserUtil {
    public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS",
            ROLE_SUPERADMIN = "ROLE_SUPERADMIN", ROLE_DB_CONSOLE = "ROLE_DB_CONSOLE",
            ROLE_USER_ADMIN = "ROLE_USER_ADMIN", ROLE_PEOPLE_ADMIN = "ROLE_PEOPLE_ADMIN",
            ROLE_PERMISSION_ADMIN = "ROLE_PERMISSION_ADMIN", ROLE_GROUP_ADMIN = "ROLE_GROUP_ADMIN";

    private static final String EDIT = "_E", INSERT = "_I", DELETE = "_D", EXECUTE = "_X";

    /**
     * Returns the edit version of a base role
     *
     * @param role the base ROLE_X
     * @return the role string with edit variant
     */
    public static String editVariant(String role) {
        return role + EDIT;
    }

    /**
     * Returns the insert version of a base role
     *
     * @param role the base ROLE_X
     * @return the role string with insert variant
     */
    public static String insertVariant(String role) {
        return role + INSERT;
    }

    /**
     * Returns the delete version of a base role
     *
     * @param role the base ROLE_X
     * @return the role string with delete variant
     */
    public static String deleteVariant(String role) {
        return role + DELETE;
    }

    /**
     * Returns the execute version of a base role
     *
     * @param role the base ROLE_X
     * @return the role string with execute variant
     */
    public static String executeVariant(String role) {
        return role + EXECUTE;
    }

    /**
     * Returns the presentation object of the current user, or null if the user
     * is browsing anonymously
     *
     * @return the current user or null
     */
    public static UserDetails getCurrentUser() {
        SecuredUser user = null;
        try {
            user = (SecuredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception ignored) {

        }
        return user;
    }

    /**
     * Returns if the current user can access the function specified in the role
     * and in that domain (if the application is spread on many domains)
     *
     * @param role   name of the role (with ROLE_ prefix)
     * @return true or false
     */
    public static boolean isCurrentUserEnabled(String role) {
        return (role.equals(ROLE_ANONYMOUS) || containsRole(role) || isCurrentUserSuperAdmin());
    }

    /**
     * Given a collection of roles, the user can access the function only
     * if it has all the roles specified in the collection
     * @param roles the collection of roles needed to access the element
     * @return {@code true} or {@code false}
     */
    public static boolean isCurrentUserEnabled(Collection<Role> roles) {
        if (roles == null || roles.isEmpty())
            return true;
        if (isCurrentUserSuperAdmin())
            return true;
        boolean tmp = true;
        for (Role role: roles) {
            tmp &= isCurrentUserEnabled(role.getCode());
        }
        return tmp;
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
        UserDetails user = getCurrentUser();
        return (user != null && (user.getAuthorities().size() > 0
                && user.getAuthorities().stream().filter(r -> r.getAuthority().equals(role)).count() > 0));
    }
}
