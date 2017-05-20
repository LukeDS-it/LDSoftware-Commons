package it.ldsoftware.primavera.mapper;

import it.ldsoftware.primavera.model.base.BaseEntity;
import it.ldsoftware.primavera.presentation.base.BaseDTO;

/**
 * A mapper is a service that translates model and view back and forth.
 *
 * @author Luca Di Stefano
 */
public interface Mapper<E extends BaseEntity, D extends BaseDTO> {

    /**
     * Converts the view item into a model item (i.e. from presentation
     * to database).
     * @param view the presentation object
     * @return the database entity
     */
    E convertToModel(D view);

    /**
     * Converts the model item into a view item (i.e. from database to
     * presentation).
     * @param model the database entity
     * @return the presentation object
     */
    D convertToView(E model);

}
