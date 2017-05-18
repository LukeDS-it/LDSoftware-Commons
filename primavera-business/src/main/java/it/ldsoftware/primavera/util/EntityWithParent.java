package it.ldsoftware.primavera.util;

/**
 * Created by luca on 11/05/16.
 * This interface specifies that the entity can have a parent,
 * and has a method to return it and set it
 */
public interface  EntityWithParent {
    ParentEntity getParent();
    void setParent(ParentEntity parent);
}
