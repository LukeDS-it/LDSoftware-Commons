package it.ldsoftware.primavera.entities.security;

import it.ldsoftware.primavera.entities.base.Lookup;
import it.ldsoftware.primavera.util.RoleCollector;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by luca on 12/04/16.
 * This entity represents a group of users
 */
@Entity
@Table(name = "fw_groups")
@AssociationOverride(name = "translations", joinTable = @JoinTable(name = "fw_groups_translations"))
public class Group extends Lookup {

    @OneToMany
    private Set<GroupRole> groupRoles = new HashSet<>();

    public Set<GroupRole> getGroupRoles() {
        return groupRoles;
    }

    public void setGroupRoles(Set<GroupRole> groupRoles) {
        this.groupRoles = groupRoles;
    }

    public void addRole(RoleCollector roleCollector) {
        groupRoles.add(new GroupRole().fromRoleCollector(roleCollector));
    }

    public void remRole(RoleCollector roleCollector) {
        groupRoles.remove(new GroupRole().fromRoleCollector(roleCollector));
    }

}
