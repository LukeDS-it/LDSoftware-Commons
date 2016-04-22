package it.ldsoftware.commons.vaadin.util;

import it.ldsoftware.commons.entities.security.Group;

/**
 * Created by luca on 22/04/16.
 */
public interface GroupAdder {
    void addGroups(Group... groups);

    void remGroups(Group... groups);
}
