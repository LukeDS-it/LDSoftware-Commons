package it.ldsoftware.rest.payload.people;

import it.ldsoftware.rest.payload.security.GroupPayload;
import it.ldsoftware.rest.payload.security.RolePayload;

import java.util.HashSet;
import java.util.Set;

/**
 * Payload for an user
 * @author luca
 */
public class UserPayload extends PersonPayload {

    private String username, password, confirmPassword, primaryEmail;
    private boolean enabled = false;

    private Set<RolePayload> userRoles = new HashSet<>();
    private Set<GroupPayload> groups = new HashSet<>();

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

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<RolePayload> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<RolePayload> userRoles) {
        this.userRoles = userRoles;
    }

    public Set<GroupPayload> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupPayload> groups) {
        this.groups = groups;
    }
}
