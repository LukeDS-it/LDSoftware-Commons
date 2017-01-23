package it.ldsoftware.primavera.entities.security;

import it.ldsoftware.primavera.entities.people.User;
import it.ldsoftware.primavera.util.RoleCollector;

import javax.persistence.*;

/**
 * Created by luca on 12/04/16.
 * This entity is different, as it represents a many-to-many association
 * between the tables "group" and "role", assigning a detail to this
 * relationship
 */
@Entity
@IdClass(UserRoleID.class)
@Table(name = "fw_user_roles")
public class UserRole implements RoleCollector {

    @Embedded
    private RoleModifiers modifiers;
    @Id
    @ManyToOne
    private User user;
    @Id
    @ManyToOne
    private Role role;

    public UserRole fromRoleCollector(RoleCollector rc) {
        role = rc.getRole();
        modifiers = rc.getModifiers();
        return this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public UserRole withRole(Role r) {
        setRole(r);
        return this;
    }

    public UserRole withDeleteAllowed(boolean value) {
        if (modifiers == null)
            modifiers = new RoleModifiers();
        modifiers.setDelete(value);
        return this;
    }

    public UserRole withEditAllowed(boolean value) {
        if (modifiers == null)
            modifiers = new RoleModifiers();
        modifiers.setEdit(value);
        return this;
    }

    public UserRole withExecuteAllowed(boolean value) {
        if (modifiers == null)
            modifiers = new RoleModifiers();
        modifiers.setExecute(value);
        return this;
    }

    public UserRole withInsertAllowed(boolean value) {
        if (modifiers == null)
            modifiers = new RoleModifiers();
        modifiers.setInsert(value);
        return this;
    }

    public UserRole withUser(User user) {
        this.user = user;
        return this;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        return role.equals(userRole.role);

    }

    @Override
    public int hashCode() {
        return role.hashCode();
    }
}
