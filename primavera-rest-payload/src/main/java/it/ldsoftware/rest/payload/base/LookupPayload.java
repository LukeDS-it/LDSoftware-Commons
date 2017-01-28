package it.ldsoftware.rest.payload.base;

import it.ldsoftware.rest.payload.lang.ShortTranslationPayload;
import it.ldsoftware.rest.payload.lang.TranslatablePayload;

/**
 * Payload for a generic lookup containing code and description
 */
public class LookupPayload extends TranslatablePayload<ShortTranslationPayload> {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
