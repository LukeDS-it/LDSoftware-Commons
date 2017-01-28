package it.ldsoftware.rest.mapper.lang;

import it.ldsoftware.primavera.entities.lang.Translation;
import it.ldsoftware.rest.payload.lang.TranslationPayload;

public abstract class TranslationMapper<E extends Translation, P extends TranslationPayload> {

    public abstract E getTranslation(P payload);

    public abstract P getPayload(E translation);

}
