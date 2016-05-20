package it.ldsoftware.primavera.query.factories;

import it.ldsoftware.primavera.query.Filter;

/**
 * Created by luca on 20/05/16.
 * The abstract filter factory is to be implemented
 * in cases where the normal filtering is not possible,
 * for example when querying on sub-properties of a collection.
 *
 * Examples of this can be found in the person entity
 * where an user could want a custom filters for telephone number or email,
 * which are all nested into the same collection of contacts.
 *
 */
public abstract class AbstractFilterFactory {

    public abstract Filter createFilterFor(String property, Object value);

}
