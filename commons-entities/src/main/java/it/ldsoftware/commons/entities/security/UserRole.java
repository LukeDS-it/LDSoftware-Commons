package it.ldsoftware.commons.entities.security;

import it.ldsoftware.commons.entities.people.User;
import it.ldsoftware.commons.util.RoleCollector;

import javax.persistence.*;

/**
 * Created by luca on 12/04/16.
 * This entity is different, as it represents a many-to-many association
 * between the tables "group" and "role", assigning a detail to this
 * relationship
 */
@Entity
@IdClass(UserRoleID.class)
public class UserRole extends RoleCollector {

    @Id
    @ManyToMany
    private User user;

    @Id
    @ManyToMany
    private Role role;

    @Embedded
    RoleModifiers modifiers;

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
}
