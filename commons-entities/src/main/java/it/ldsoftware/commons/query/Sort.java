package it.ldsoftware.commons.query;

import java.io.Serializable;

/**
 * Created by luca on 19/04/16.
 */
public class Sort implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Sort EMPTY = new Sort("id", true);

    String property;
    boolean sortAscending;

    public Sort(String property, boolean sortAscending) {
        this.property = property;
        this.sortAscending = sortAscending;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public boolean isSortAscending() {
        return sortAscending;
    }

    public void setSortAscending(boolean sortAscending) {
        this.sortAscending = sortAscending;
    }

}