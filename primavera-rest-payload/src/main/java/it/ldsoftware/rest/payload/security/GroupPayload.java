package it.ldsoftware.rest.payload.security;

import it.ldsoftware.rest.payload.base.LookupPayload;

import java.util.HashSet;
import java.util.Set;

/**
 * Payload for a group. A group contains a set of roles as well as name and description
 */
public class GroupPayload extends LookupPayload {

    private Set<RolePayload> roles = new HashSet<>();

    public Set<RolePayload> getRoles() {
        return roles;
    }

    public void setRoles(Set<RolePayload> roles) {
        this.roles = roles;
    }
}
