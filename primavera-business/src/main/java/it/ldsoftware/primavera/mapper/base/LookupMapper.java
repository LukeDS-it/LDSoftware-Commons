package it.ldsoftware.primavera.mapper.base;

import it.ldsoftware.primavera.mapper.lang.TranslatableMapper;
import it.ldsoftware.primavera.model.base.Lookup;
import it.ldsoftware.primavera.model.lang.ShortTranslation;
import it.ldsoftware.primavera.presentation.base.LookupDTO;

/**
 * @author Luca Di Stefano
 */
public abstract class LookupMapper<E extends Lookup, D extends LookupDTO> extends TranslatableMapper<E, ShortTranslation, D> {

    @Override
    public E getTranslatableInstance(D view) {
        E e = getLookupInstance(view);
        e.setCode(view.getCode());
        return e;
    }

    @Override
    public D getTranslatableView(E model, ShortTranslation translation) {
        D d = getLookupDTOInstance(model);
        d.setCode(model.getCode());
        d.setDescription(translation.getDescription());
        return d;
    }

    /**
     * Must return the instance of the implementaition of {@link Lookup}
     * with instance fields already set
     *
     * @param view view (presentation) data
     * @return model (database) data
     */
    public abstract E getLookupInstance(D view);

    /**
     * Must return the instance of the implementation of {@link LookupDTO}
     * with instance fields already set
     *
     * @param model model (database) data
     * @return view (presentation) data
     */
    public abstract D getLookupDTOInstance(E model);
}
