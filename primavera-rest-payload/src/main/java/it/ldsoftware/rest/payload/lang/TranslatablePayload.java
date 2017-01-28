package it.ldsoftware.rest.payload.lang;

import it.ldsoftware.rest.payload.base.BasePayload;

import java.util.HashMap;
import java.util.Map;

/**
 * The translatable payload is a payload that contains
 * language references.
 */
public class TranslatablePayload<T extends TranslationPayload> extends BasePayload {

    private Map<String, T> translations = new HashMap<>();

    private String defaultLang;

    public String getDefaultLang() {
        return defaultLang;
    }

    public void setDefaultLang(String defaultLang) {
        this.defaultLang = defaultLang;
    }

    public Map<String, T> getTranslations() {
        return translations;
    }

    public void setTranslations(Map<String, T> translations) {
        this.translations = translations;
    }

    public void addTranslation(String lang, T element) {
        translations.put(lang, element);
    }
}
