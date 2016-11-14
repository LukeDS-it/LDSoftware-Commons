package it.ldsoftware.primavera.entities.lang;

import it.ldsoftware.primavera.entities.base.BaseEntity;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

/**
 * Created by luca on 12/04/16.
 * This class represents an abstract translation.
 * There are no fields in this, so each translation must implement
 * the actual database representation
 */
@Embeddable
@MappedSuperclass
public abstract class Translation extends BaseEntity {

}
