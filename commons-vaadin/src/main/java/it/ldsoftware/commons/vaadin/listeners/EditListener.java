package it.ldsoftware.commons.vaadin.listeners;

import it.ldsoftware.commons.dto.base.BaseDTO;

import java.util.List;

/**
 * Created by luca on 19/04/16.
 * Listens an "edit" action on a list of DTO
 */
public interface EditListener<D extends BaseDTO> {
    void performEdit(List<D> dList);
}
