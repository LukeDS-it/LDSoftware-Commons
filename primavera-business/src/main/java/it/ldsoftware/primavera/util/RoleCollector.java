package it.ldsoftware.primavera.util;

import it.ldsoftware.primavera.model.security.Role;
import it.ldsoftware.primavera.model.security.RoleModifiers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by luca on 12/04/16.
 * Utility class to help collect roles
 */
public interface RoleCollector {

    Role getRole();

    RoleModifiers getModifiers();

    default Collection<String> getActualRoles() {
        String r = getRole().getCode();
        List<String> roles = new ArrayList<>();
        roles.add(r);
        roles.addAll(getModifiers().getRoleWithModifiers(r));
        return roles;
    }
}
