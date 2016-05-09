package it.ldsoftware.primavera.entities.security;

import it.ldsoftware.primavera.util.RoleCollector;

import javax.persistence.*;

/**
 * Created by luca on 12/04/16.
 * This entity is different, as it represents a many-to-many association
 * between the tables "group" and "role", assigning a detail to this
 * relationship
 */
@Entity
@IdClass(GroupRoleID.class)
public class GroupRole implements RoleCollector {

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

    public GroupRole fromRoleCollector(RoleCollector rc) {
        role = rc.getRole();
        modifiers = rc.getModifiers();
        return this;
    }

    @Override
    public RoleModifiers getModifiers() {
        return modifiers;
    }

    public void setModifiers(RoleModifiers modifiers) {
        this.modifiers = modifiers;
    }

    public String getAuthority() {
        return getRole().getCode();
    }

    public boolean isInsertAllowed() {
        return getModifiers().isInsert();
    }

    public boolean isEditAllowed() {
        return getModifiers().isEdit();
    }

    public boolean isDeleteAllowed() {
        return getModifiers().isDelete();
    }

    public boolean isExecuteAllowed() {
        return getModifiers().isExecute();
    }
}
