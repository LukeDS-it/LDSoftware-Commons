package it.ldsoftware.primavera.model.security;

import it.ldsoftware.primavera.model.base.Lookup;

import javax.persistence.AssociationOverride;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.Table;

/**
 * Created by luca on 12/04/16.
 * This entity represents a role in the database
 */
@Entity
@Table(name = "fw_roles")
@AssociationOverride(name = "translations", joinTable = @JoinTable(name = "fw_roles_translations"))
public class Role extends Lookup {
}
