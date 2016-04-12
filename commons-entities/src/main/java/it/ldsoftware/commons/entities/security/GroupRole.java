package it.ldsoftware.commons.entities.security;

import it.ldsoftware.commons.util.RoleCollector;

import javax.persistence.*;

/**
 * Created by luca on 12/04/16.
 * This entity is different, as it represents a many-to-many association
 * between the tables "group" and "role", assigning a detail to this
 * relationship
 */
@Entity
@IdClass(GroupRoleID.class)
public class GroupRole extends RoleCollector {

    @Id
    @ManyToOne
    private Group group;

    @Id
    @ManyToOne
    private Role role;

    @Embedded
    private RoleModifiers modifiers;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public RoleModifiers getModifiers() {
        return modifiers;
    }

    public void setModifiers(RoleModifiers modifiers) {
        this.modifiers = modifiers;
    }
}
