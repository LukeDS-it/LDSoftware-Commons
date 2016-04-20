package it.ldsoftware.commons.vaadin.util;

import com.vaadin.server.Resource;
import it.ldsoftware.commons.i18n.LocalizationService;

import java.util.Locale;

import static it.ldsoftware.commons.vaadin.theme.BaseResources.getFlag;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static java.util.Locale.ITALIAN;

/**
 * Created by luca on 20/04/16.
 * Object that represents a supported language
 */
public class SupportedLanguage {

    private String caption, langName;
    private Resource icon;
    private Locale locale;

    public static final SupportedLanguage[] supportedLanguages =
            new SupportedLanguage[]{
                    new SupportedLanguage().withCaption("language.italian").withLocale(ITALIAN),
                    new SupportedLanguage().withCaption("language.english").withLocale(ENGLISH),
                    new SupportedLanguage().withCaption("language.german").withLocale(GERMAN)
            };

    public SupportedLanguage() {

    }

    public String getCaption() {
        return caption;
    }

    public SupportedLanguage withCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public Resource getIcon() {
        return icon;
    }

    public SupportedLanguage withIcon(Resource icon) {
        this.icon = icon;
        return this;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

    public SupportedLanguage translate(LocalizationService svc) {
        this.setLangName(svc.translate(caption));
        return this;
    }

    @Override
    public String toString() {
        return locale.getLanguage();
    }

    public Locale getLocale() {
        return locale;
    }

    public SupportedLanguage withLocale(Locale locale) {
        this.locale = locale;
        this.withIcon(getFlag(locale));
        return this;
    }

}
