package it.ldsoftware.commons.vaadin.listeners;

import it.ldsoftware.commons.dto.base.BaseDTO;

import java.util.List;

/**
 * Created by luca on 19/04/16.
 * Listens a "delete" event on a list of DTO
 */
public interface DeleteListener<D extends BaseDTO> {
    void performDelete(List<D> dList);
}
