package it.ldsoftware.primavera.mapper.base;

import it.ldsoftware.primavera.mapper.lang.TranslatableMapper;
import it.ldsoftware.primavera.model.base.Lookup;
import it.ldsoftware.primavera.model.lang.ShortTranslation;
import it.ldsoftware.primavera.presentation.base.LookupDTO;
import it.ldsoftware.primavera.presentation.lang.ShortTranslationDTO;

/**
 * @author Luca Di Stefano
 */
public abstract class LookupMapper<E extends Lookup, D extends LookupDTO> extends TranslatableMapper<E, ShortTranslation, D, ShortTranslationDTO> {

    @Override
    public E getTranslatableModel(D view) {
        E e = getLookupInstance(view);
        e.setCode(view.getCode());
        return e;
    }

    @Override
    public D getTranslatableView(E model) {
        D d = getLookupDTOInstance(model);
        d.setCode(model.getCode());
        return d;
    }

    @Override
    public ShortTranslation getTranslationModel(ShortTranslationDTO view) {
        ShortTranslation model = new ShortTranslation();
        model.setDescription(view.getDescription());
        model.setLanguage(view.getLanguage());
        return model;
    }

    @Override
    public ShortTranslationDTO getTranslationView(ShortTranslation model) {
        ShortTranslationDTO view = new ShortTranslationDTO();
        view.setDescription(model.getDescription());
        view.setLanguage(model.getLanguage());
        return view;
    }

    /**
     * Must return the instance of the implementation of {@link Lookup}
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
