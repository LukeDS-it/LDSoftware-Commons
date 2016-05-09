package it.ldsoftware.primavera.vaadin.util;

import it.ldsoftware.primavera.util.RoleCollector;

/**
 * Created by luca on 22/04/16.
 *
 */
public interface RoleAdder {
    void addRoles(RoleCollector... roles);

    void remRoles(RoleCollector... roles);
}
