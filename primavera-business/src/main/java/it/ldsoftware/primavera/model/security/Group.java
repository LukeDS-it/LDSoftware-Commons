package it.ldsoftware.primavera.model.security;

import it.ldsoftware.primavera.model.base.Lookup;
import it.ldsoftware.primavera.util.RoleCollector;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by luca on 12/04/16.
 * This entity represents a group of users
 */
@Entity
@Getter @Setter
@Table(name = "fw_groups")
@AssociationOverride(name = "translations", joinTable = @JoinTable(name = "fw_groups_translations"))
public class Group extends Lookup {

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Set<GroupRole> groupRoles = new HashSet<>();

    public void addRole(RoleCollector roleCollector) {
        groupRoles.add(new GroupRole().fromRoleCollector(roleCollector));
    }

    public void remRole(RoleCollector roleCollector) {
        groupRoles.remove(new GroupRole().fromRoleCollector(roleCollector));
    }

}
