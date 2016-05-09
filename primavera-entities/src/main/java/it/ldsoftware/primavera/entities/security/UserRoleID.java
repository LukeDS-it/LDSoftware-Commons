package it.ldsoftware.primavera.entities.security;

import it.ldsoftware.primavera.entities.people.User;

import java.io.Serializable;

/**
 * Created by luca on 12/04/16.
 * The primary key of the user_role table is on the
 * two ids of the group and the role, so that no group
 * can be paired with the same role twice
 */
public class UserRoleID implements Serializable {
    User user;
    Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRoleID that = (UserRoleID) o;

        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return role != null ? role.equals(that.role) : that.role == null;

    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
