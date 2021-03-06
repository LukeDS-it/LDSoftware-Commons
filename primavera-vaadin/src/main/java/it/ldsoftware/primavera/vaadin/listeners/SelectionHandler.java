package it.ldsoftware.primavera.vaadin.listeners;

import it.ldsoftware.primavera.presentation.base.BaseDTO;

import java.util.List;

/**
 * Created by luca on 20/04/16.
 * Basic functional interface that defines what happens when a list of
 * DTO is selected
 */
public interface SelectionHandler<D extends BaseDTO<?>> {
    void handleSelection(List<D> dList);
}
