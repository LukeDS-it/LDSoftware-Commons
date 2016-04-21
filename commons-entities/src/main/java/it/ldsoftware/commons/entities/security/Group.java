package it.ldsoftware.commons.entities.security;

import it.ldsoftware.commons.entities.base.Lookup;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by luca on 12/04/16.
 * This entity represents a group of users
 */
@Entity
@Table(name = "sw_group")
public class Group extends Lookup<GroupTranslation> {

    @OneToMany
    private Set<GroupRole> groupRoles = new HashSet<>();

    public Set<GroupRole> getGroupRoles() {
        return groupRoles;
    }

    public void setGroupRoles(Set<GroupRole> groupRoles) {
        this.groupRoles = groupRoles;
    }
}
