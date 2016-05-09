package it.ldsoftware.primavera.entities.security;

import java.io.Serializable;

/**
 * Created by luca on 12/04/16.
 * The primary key of the group_role table is on the
 * two ids of the group and the role, so that no group
 * can be paired with the same role twice
 */
public class GroupRoleID implements Serializable {
    Group group;
    Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupRoleID that = (GroupRoleID) o;

        if (group != null ? !group.equals(that.group) : that.group != null) return false;
        return role != null ? role.equals(that.role) : that.role == null;

    }

    @Override
    public int hashCode() {
        int result = group != null ? group.hashCode() : 0;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
