package it.ldsoftware.commons.entities.base;

import it.ldsoftware.commons.entities.lang.ShortTranslation;

import javax.persistence.MappedSuperclass;

/**
 * Created by luca on 12/04/16.
 * Translation for a lookup type. It will only contain the
 * 255 characters long description
 */
@MappedSuperclass
public abstract class LookupTranslation<L extends Lookup> extends ShortTranslation<L> {

}
