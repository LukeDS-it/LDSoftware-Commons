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
        model.setDefaultLang(view.getLang());
        return model;
    }

    @Override
    public D getViewInstance(E model) {
        T translation = model.getTranslation(""); // TODO language service
        D view = getTranslatableView(model, translation);
        view.setLang(translation.getLanguage());
        return view;
    }

    public abstract E getTranslatableInstance(D view);

    public abstract D getTranslatableView(E model, T translation);
}
