package it.ldsoftware.primavera.vaadin.data;


import com.vaadin.data.Container.Filter;

import java.util.Collection;

/**
 * Created by luca on 23/05/16.
 * The filter consumer is a functional interface that describes how
 * a list of filter is to be used
 */
public interface FilterConsumer {
    void useFilters(Collection<Filter> filters) ;
}
