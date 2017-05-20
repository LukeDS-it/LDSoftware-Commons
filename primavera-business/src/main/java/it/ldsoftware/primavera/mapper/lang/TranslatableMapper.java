package it.ldsoftware.primavera.mapper.lang;

import it.ldsoftware.primavera.mapper.base.BaseMapper;
import it.ldsoftware.primavera.model.lang.Translatable;
import it.ldsoftware.primavera.model.lang.Translation;
import it.ldsoftware.primavera.presentation.lang.TranslatableDTO;

/**
 * Abstract mapper for translatable entities.
 *
 * @author Luca Di Stefano
 */
public abstract class TranslatableMapper<E extends Translatable<T>, T extends Translation, D extends TranslatableDTO> extends BaseMapper<E, D> {

    @Override
    public E getModelInstance(D view) {
        E model = getTranslatableInstance(view);
        model.setDefaultLang(view.getDefaultLang());
        return model;
    }

    @Override
    public D getViewInstance(E model) {
        T translation = model.getTranslation(""); // TODO language service
        D view = getTranslatableView(model, translation);
        view.setLang(translation.getLanguage());
        return view;
    }

    /**
     * Must return a {@link Translatable} instance with own fields already set
     *
     * @param view view (presentation) object
     * @return model (database) object
     */
    public abstract E getTranslatableInstance(D view);

    /**
     * Must return a {@link TranslatableDTO} instance with own fields already set
     *
     * @param model       model (database) instance
     * @param translation the current translation that is being taken into account
     * @return view (presentation) object
     */
    public abstract D getTranslatableView(E model, T translation);
}
