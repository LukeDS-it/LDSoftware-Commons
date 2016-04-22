package it.ldsoftware.commons.vaadin.util;

import it.ldsoftware.commons.util.RoleCollector;

/**
 * Created by luca on 22/04/16.
 */
public interface RoleAdder {
    void addRoles(RoleCollector... roles);

    void remRoles(RoleCollector... roles);
}
