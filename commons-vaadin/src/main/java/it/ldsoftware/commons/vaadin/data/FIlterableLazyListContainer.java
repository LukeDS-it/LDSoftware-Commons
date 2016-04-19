package it.ldsoftware.commons.vaadin.data;

import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Container.Sortable;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.data.util.filter.UnsupportedFilterException;
import org.vaadin.viritin.ListContainer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static it.ldsoftware.commons.query.FilterOperator.AND;
import static java.util.Collections.unmodifiableCollection;

/**
 * Created by luca on 19/04/16.
 * Container for filterable lazy lists
 */
public class FIlterableLazyListContainer<T> extends ListContainer<T> implements Filterable, Sortable {

    private static final long serialVersionUID = 1L;

    private final Set<Filter> filters = new HashSet<>();

    public FIlterableLazyListContainer(FilterableLazyList<T> backingList) {
        super(backingList);
    }

    public FIlterableLazyListContainer(Class<T> type, FilterableLazyList<T> backingList) {
        super(type, backingList);
    }


    @Override
    public void addContainerFilter(Filter filter) throws UnsupportedFilterException {
        if (filters.add(filter)) {
            ((FilterableLazyList<T>) getBackingList()).addFilter(convertFilter(filter));
            applyFilters();
        }
    }

    @Override
    public void removeContainerFilter(Filter filter) {
        if (filters.remove(filter)) {
            ((FilterableLazyList<T>) getBackingList()).removeFilter(convertFilter(filter));
            applyFilters();
        }
    }

    @Override
    public void removeAllContainerFilters() {
        if (!filters.isEmpty()) {
            filters.clear();
            ((FilterableLazyList<T>) getBackingList()).removeAllFilters();
            applyFilters();
        }
    }

    @Override
    public Collection<Filter> getContainerFilters() {
        return unmodifiableCollection(filters);
    }

    private void applyFilters() {
        ((FilterableLazyList<T>) getBackingList()).reset();
        super.fireItemSetChange();
    }

    private it.ldsoftware.commons.query.Filter convertFilter(Filter filter) {
        if (!(filter instanceof SimpleStringFilter))
            throw new UnsupportedFilterException("Only SimpleStringFilter is supported");
        return convertFilter((SimpleStringFilter) filter);
    }

    private it.ldsoftware.commons.query.Filter convertFilter(SimpleStringFilter filter) {
        return new it.ldsoftware.commons.query.Filter(filter.getPropertyId().toString(), filter.getFilterString() + "%", false, AND);
    }

}