package it.ldsoftware.primavera.mapper.base;

import it.ldsoftware.primavera.mapper.Mapper;
import it.ldsoftware.primavera.model.base.BaseEntity;
import it.ldsoftware.primavera.presentation.base.BaseDTO;

/**
 * @author Luca Di Stefano
 */
public abstract class BaseMapper<E extends BaseEntity, D extends BaseDTO> implements Mapper<E, D> {

    @Override
    public E convertToModel(D view) {
        E model = getModelInstance(view);
        model.setId(view.getId());
        return model;
    }

    @Override
    public D convertToView(E model) {
        D view = getViewInstance(model);
        view.setId(model.getId());
        return view;
    }

    /**
     * Must return a model (database) instance with custom fields
     * already set. The Base mapper will insert the rest.
     *
     * @param view the view object from which take the custom fields.
     * @return new E() with fields
     */
    public abstract E getModelInstance(D view);

    /**
     * Must return a view (presentation) instance with custom fields
     * already set
     *
     * @param model the model object from which to take the custom fields.
     * @return new D() with fields
     */
    public abstract D getViewInstance(E model);
}
