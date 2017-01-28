package it.ldsoftware.rest.mapper.lang;

import it.ldsoftware.primavera.entities.lang.Translatable;
import it.ldsoftware.primavera.entities.lang.Translation;
import it.ldsoftware.rest.mapper.base.BaseMapper;
import it.ldsoftware.rest.payload.lang.TranslatablePayload;
import it.ldsoftware.rest.payload.lang.TranslationPayload;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class TranslatableMapper<T extends Translation, U extends TranslationPayload, E extends Translatable<T>, P extends TranslatablePayload<U>>
        extends BaseMapper<E, P> {

    private TranslationMapper<T, U> translationMapper;

    @Override
    public void initEntity(E entity, P payload) {
        entity.setDefaultLang(payload.getDefaultLang());
        payload.getTranslations()
                .forEach((lang, transl) -> entity.addTranslation(lang, translationMapper.getTranslation(transl)));
    }

    @Override
    public void initPayload(P payload, E entity) {
        payload.setDefaultLang(entity.getDefaultLang());
        entity.getTranslations()
                .forEach((lang, transl) -> payload.addTranslation(lang, translationMapper.getPayload(transl)));
    }

    @Autowired
    public void setTranslationMapper(TranslationMapper<T, U> translationMapper) {
        this.translationMapper = translationMapper;
    }
}
