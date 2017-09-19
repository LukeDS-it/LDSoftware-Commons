package it.ldsoftware.primavera.model.people;

import it.ldsoftware.primavera.model.security.Group;
import it.ldsoftware.primavera.model.security.UserRole;
import it.ldsoftware.primavera.presentation.enums.PersonType;
import it.ldsoftware.primavera.util.RoleCollector;
import it.ldsoftware.primavera.validation.groups.NewUserValidationGroup;
import lombok.Getter;
import lombok.Setter;

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
@Getter @Setter
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

    @NotNull
    @Column(nullable = false, name = "primary_email")
    private String primaryEmail;

    private boolean enabled = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
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

}
