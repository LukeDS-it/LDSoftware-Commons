package it.ldsoftware.primavera.util;

/**
 * This interface specifies that the entity can have a parent,
 * and has a method to return it and set it
 *
 * @author Luca Di Stefano
 */
public interface  EntityWithParent {
    ParentEntity getParent();
    void setParent(ParentEntity parent);
}
