package it.ldsoftware.rest.mapper.lang;

import it.ldsoftware.primavera.entities.lang.ShortTranslation;
import it.ldsoftware.rest.payload.lang.ShortTranslationPayload;
import org.springframework.stereotype.Service;

@Service
public class ShortTranslationMapper extends TranslationMapper<ShortTranslation, ShortTranslationPayload> {

    @Override
    public ShortTranslation getTranslation(ShortTranslationPayload payload) {
        return new ShortTranslation().withContent(payload.getDescription());
    }

    @Override
    public ShortTranslationPayload getPayload(ShortTranslation translation) {
        return new ShortTranslationPayload().withDescription(translation.getDescription());
    }
}
