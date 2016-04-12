package it.ldsoftware.commons.entities.security;

import it.ldsoftware.commons.entities.people.User;

/**
 * Created by luca on 12/04/16.
 * The primary key of the user_role table is on the
 * two ids of the group and the role, so that no group
 * can be paired with the same role twice
 */
public class UserRoleID {
    User user;
    Role role;
}
