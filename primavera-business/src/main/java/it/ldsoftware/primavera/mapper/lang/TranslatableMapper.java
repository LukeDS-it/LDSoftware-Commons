package it.ldsoftware.primavera.mapper.lang;

import it.ldsoftware.primavera.mapper.base.BaseMapper;
import it.ldsoftware.primavera.model.lang.Translatable;
import it.ldsoftware.primavera.model.lang.Translation;
import it.ldsoftware.primavera.presentation.lang.TranslatableDTO;
import it.ldsoftware.primavera.presentation.lang.TranslationDTO;

/**
 * Abstract mapper for translatable entities.
 *
 * @author Luca Di Stefano
 */
public abstract class TranslatableMapper<E extends Translatable<T>, T extends Translation, D extends TranslatableDTO<Q>, Q extends TranslationDTO> extends BaseMapper<E, D> {

    @Override
    public E getModelInstance(D view) {
        E model = getTranslatableModel(view);
        model.setDefaultLang(view.getDefaultLang());
        view.getTranslations().forEach((s, q) -> model.addTranslation(s, getTranslationModel(q)));
        return model;
    }

    @Override
    public D getViewInstance(E model) {
        D view = getTranslatableView(model);
        view.setDefaultLang(model.getDefaultLang());
        model.getTranslations().forEach((s, t) -> view.addTranslation(s, getTranslationView(t)));
        return view;
    }

    /**
     * Must return a {@link Translatable} instance with own fields already set
     *
     * @param view view (presentation) object
     * @return model (database) object
     */
    public abstract E getTranslatableModel(D view);

    /**
     * Must return a {@link TranslatableDTO} instance with own fields already set
     *
     * @param model       model (database) instance
     * @return view (presentation) object
     */
    public abstract D getTranslatableView(E model);

    public abstract T getTranslationModel(Q q);

    public abstract Q getTranslationView(T t);
}
