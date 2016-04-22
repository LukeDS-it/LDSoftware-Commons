package it.ldsoftware.commons.entities.lang;

import it.ldsoftware.commons.entities.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.MapKey;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;


/**
 * Created by luca on 12/04/16.
 * This class represents an entity that can be translated to
 * another language, and is parametrized for its own translation.
 * Implementing this class means creating a translatable and a translation
 * for your objects, this will automatically create
 * the tables for the object, the translation and the relationship between
 * translations (even if this is not optimal DB-wise)
 */
@MappedSuperclass
@SuppressWarnings("unchecked")
public abstract class Translatable<T extends Translation> extends BaseEntity {

    @Column(length = 2)
    private String defaultLang;

    @MapKey(name = "lang")
    @OneToMany(fetch = EAGER, cascade = ALL, orphanRemoval = true, mappedBy = "master")
    private final Map<String, T> translations = new HashMap<>();

    public Map<String, T> getTranslations() {
        return translations;
    }

    public void addTranslation(String lang, T translation) {
        if (lang == null || lang.length() != 2)
            throw new IllegalArgumentException("error.lang.code");
        if (hasLanguage(lang))
            throw new IllegalArgumentException("warning.duplicate.translation");

        translation.setMaster(this);
        if (translations.size() == 0 && defaultLang == null)
            defaultLang = translation.getLang();

        translations.put(lang, translation);
    }

    public void removeTranslation(String lang) {
        if (lang != null)
            translations.remove(lang);
    }

    /**
     * Returns if the selected language already exists
     *
     * @param lang the two-character representation of the locale
     * @return true or false
     */
    public boolean hasLanguage(String lang) {
        return translations.containsKey(lang);
    }

    /**
     * Returns the translation
     *
     * @param lang the two-character representation of the locale
     * @return the translation object in the selected language, the default translation if the selected
     * language is not available, or null if none of them are available.
     */
    public T getTranslation(String lang) {
        T translation = translations.get(lang);
        if (translation != null)
            return translation;

        return translations.get(defaultLang);
    }

    /**
     * Gets the translation in the selected locale
     *
     * @param l the locale
     * @return the translation with that locale
     */
    public T getTranslation(Locale l) {
        return getTranslation(l.getLanguage());
    }

    /**
     * Returns the translation in the selected language, forcing returning "null" if
     * the selected language is not found
     *
     * @param lang 2-char string of the language code
     * @return the translation object in the selected language, or null if not found
     */
    public T getTranslationForced(String lang) {
        return translations.get(lang);
    }

    public String getDefaultLang() {
        return defaultLang;
    }

    public void setDefaultLang(String defaultLang) {
        this.defaultLang = defaultLang;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + getTranslation(defaultLang).toString();
    }
}
