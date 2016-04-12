package it.ldsoftware.commons.entities.security;

import it.ldsoftware.commons.entities.base.Lookup;

import javax.persistence.Entity;

/**
 * Created by luca on 12/04/16.
 * This entity represents a role in the database
 */
@Entity
public class Role extends Lookup<RoleTranslation> {
}
