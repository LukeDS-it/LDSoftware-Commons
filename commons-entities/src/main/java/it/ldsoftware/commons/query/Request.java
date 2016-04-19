package it.ldsoftware.commons.query;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by luca on 19/04/16.
 */
public class Request implements Serializable {

    private int firstRow;
    private int pageSize;

    private Sort sort;
    private Set<Filter> filters = new HashSet<>();


    public Request withFilters(Set<Filter> filters) {
        this.filters = filters;
        return this;
    }

    public Request withSortBy(Sort sortBy) {
        sort = sortBy;
        return this;
    }

    public Request withPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Request withFirstRow(int i) {
        firstRow = i;
        return this;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(int firstRow) {
        this.firstRow = firstRow;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public Set<Filter> getFilters() {
        return filters;
    }

    public void setFilters(Set<Filter> filters) {
        this.filters = filters;
    }

}