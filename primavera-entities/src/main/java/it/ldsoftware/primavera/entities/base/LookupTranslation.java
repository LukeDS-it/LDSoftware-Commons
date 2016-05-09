package it.ldsoftware.primavera.entities.base;

import it.ldsoftware.primavera.entities.lang.ShortTranslation;

import javax.persistence.MappedSuperclass;

/**
 * Created by luca on 12/04/16.
 * Translation for a lookup type. It will only contain the
 * 255 characters long description
 */
@MappedSuperclass
public abstract class LookupTranslation<L extends Lookup> extends ShortTranslation<L> {

}
