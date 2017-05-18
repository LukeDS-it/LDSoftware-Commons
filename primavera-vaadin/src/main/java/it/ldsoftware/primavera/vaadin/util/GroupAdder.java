package it.ldsoftware.primavera.vaadin.util;

import it.ldsoftware.primavera.model.security.Group;

/**
 * Created by luca on 22/04/16.
 */
public interface GroupAdder {
    void addGroups(Group... groups);

    void remGroups(Group... groups);
}
