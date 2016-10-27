package it.ldsoftware.primavera.entities.people;

import it.ldsoftware.primavera.entities.security.Group;
import it.ldsoftware.primavera.entities.security.UserRole;
import it.ldsoftware.primavera.util.PersonType;
import it.ldsoftware.primavera.util.RoleCollector;
import it.ldsoftware.primavera.validation.groups.NewUserValidationGroup;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by luca on 11/04/16.
 *
 * @author luca
 *         An User is a @{link Person} that can log in to the program
 */
@Entity
@Table(name = "fw_users")
@PrimaryKeyJoinColumn(name = "id")
public class User extends Person {

    @NotNull
    @Column(nullable = false)
    private String username;
    @Column(length = 60, nullable = false)
    @NotNull(groups = NewUserValidationGroup.class)
    private String password;
    @Transient
    @NotNull(groups = NewUserValidationGroup.class)
    private String confirmPassword;
    @Column(nullable = false)
    @NotNull
    private String primaryEmail;
    private boolean enabled = false;
    @OneToMany
    private Set<UserRole> userRoles = new HashSet<>();
    @ManyToMany
    private Set<Group> groups = new HashSet<>();

    public User() {
        setPersonType(PersonType.REAL);
    }

    @Override
    public String toString() {
        return super.toString() + ", " + getUsername();
    }

    public void addRoles(RoleCollector... roles) {
        for (RoleCollector rc : roles) {
            userRoles.add(new UserRole().fromRoleCollector(rc).withUser(this));
        }
    }

    public void remRoles(RoleCollector... roles) {
        for (RoleCollector rc : roles) {
            userRoles.remove(new UserRole().fromRoleCollector(rc));
        }
    }

    public void addGroups(Group... groups) {
        Collections.addAll(this.groups, groups);
    }

    public void remGroups(Group... groups) {
        for (Group g : groups) this.groups.remove(g);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }
}
