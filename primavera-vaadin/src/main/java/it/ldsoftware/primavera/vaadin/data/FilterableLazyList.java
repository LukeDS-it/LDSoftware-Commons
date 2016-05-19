package it.ldsoftware.primavera.vaadin.data;

import it.ldsoftware.primavera.query.Filter;
import it.ldsoftware.primavera.query.Request;
import it.ldsoftware.primavera.query.Sort;
import org.vaadin.viritin.LazyList;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static it.ldsoftware.primavera.query.Sort.EMPTY;

/**
 * Created by luca on 19/04/16.
 * Lazy list with filter and better sort support
 */
public class FilterableLazyList<T> extends LazyList<T> {

    private static final long serialVersionUID = 1L;

    private final FilterableCountProvider countProvider;
    private final FilterablePagingProvider<T> pagingProvider;
    private final int pageSize;

    private Sort sortBy = EMPTY;
    private Set<Filter> filters = new HashSet<>();

    public static <T> FilterableLazyList<T> of(FilterablePagingProvider<T> p, FilterableCountProvider c, int i) {
        WrappedRemoteCountProvider w = new WrappedRemoteCountProvider(c);
        FilterableLazyList<T> list = new FilterableLazyList<>(p, w, i);
        w.countProvider.set(new FilterableCountProvider() {
            private static final long serialVersionUID = 1L;

            @Override
            public int size() {
                return list.filteredSize();
            }

            @Override
            public int size(Request request) {
                return c.size(request);
            }
        });
        return list;
    }

    private FilterableLazyList(FilterableEntityProvider<T> pagingProvider, int pageSize) {
        super(pagingProvider, pageSize);
        this.pageSize = pageSize;
        this.pagingProvider = pagingProvider;
        this.countProvider = pagingProvider;
    }

    private FilterableLazyList(FilterablePagingProvider<T> pagingProvider, FilterableCountProvider countProvider,
                               int pageSize) {
        super(countProvider, pageSize);
        this.pageSize = pageSize;
        this.countProvider = countProvider;
        this.pagingProvider = pagingProvider;
    }

    public FilterableLazyList(FilterablePagingProvider<T> pagingProvider, FilterableCountProvider countProvider) {
        this(pagingProvider, countProvider, DEFAULT_PAGE_SIZE);
    }

    public FilterableLazyList(FilterableEntityProvider<T> pagingProvider) {
        this(pagingProvider, DEFAULT_PAGE_SIZE);
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void removeFilter(Filter filter) {
        Filter tmp = filters.stream().filter(filter::equals).findFirst().get();
        filters.remove(tmp);
    }

    public void removeAllFilters() {
        filters.clear();
    }

    protected int filteredSize() {
        return countProvider.size(new Request().withFilters(filters));
    }

    @Override
    protected List<T> findEntities(int i) {
        Request req = new Request().withFirstRow(i).withPageSize(pageSize).withSortBy(sortBy).withFilters(filters);
        return pagingProvider.findEntities(req);
    }

    @Override
    public int size() {
        return super.size();
    }

	/*
     * ************************************************
	 * Interfaces used to search elements
	 * ************************************************
	 */

    public interface FilterablePagingProvider<T> extends Serializable {
        List<T> findEntities(Request request);
    }

    public interface FilterableCountProvider extends CountProvider {
        int size(Request request);

        @Override
        default int size() {
            return size(new Request());
        }
    }

    public interface FilterableEntityProvider<T> extends FilterablePagingProvider<T>, FilterableCountProvider {

    }

	/*
     * ****************************
	 *      Support classes
	 * ****************************
	 */

    private static class WrappedRemoteCountProvider implements FilterableCountProvider {
        private static final long serialVersionUID = 1L;
        private final AtomicReference<FilterableCountProvider> countProvider;

        public WrappedRemoteCountProvider(FilterableCountProvider countProvider) {
            this.countProvider = new AtomicReference<>(countProvider);
        }

        @Override
        public int size() {
            return countProvider.get().size();
        }

        @Override
        public int size(Request request) {
            return countProvider.get().size(request);
        }
    }


}
