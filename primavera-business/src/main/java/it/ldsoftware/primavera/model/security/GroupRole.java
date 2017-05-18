package it.ldsoftware.primavera.model.security;

import it.ldsoftware.primavera.util.RoleCollector;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by luca on 12/04/16.
 * This entity is different, as it represents a many-to-many association
 * between the tables "group" and "role", assigning a detail to this
 * relationship
 */
@Entity
@IdClass(GroupRoleID.class)
@Getter @Setter
@Table(name = "fw_group_roles")
public class GroupRole implements RoleCollector {

    @Id
    @ManyToOne
    private Group group;

    @Id
    @ManyToOne
    private Role role;

    @Embedded
    private RoleModifiers modifiers;

    public GroupRole fromRoleCollector(RoleCollector rc) {
        role = rc.getRole();
        modifiers = rc.getModifiers();
        return this;
    }
}
