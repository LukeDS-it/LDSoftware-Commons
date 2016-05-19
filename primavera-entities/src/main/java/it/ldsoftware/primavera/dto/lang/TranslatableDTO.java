package it.ldsoftware.primavera.dto.lang;

import it.ldsoftware.primavera.dto.base.BaseDTO;
import it.ldsoftware.primavera.entities.lang.Translatable;

import java.util.List;
import java.util.Locale;

/**
 * Created by luca on 19/05/16.
 *
 */
public class TranslatableDTO<E extends Translatable> extends BaseDTO<E> {
    public static final String FIELD_LANG = "lang", FIELD_MASTER = "master";

    private String lang;

    public TranslatableDTO() {

    }

    @Override
    public List<String> _fields() {
        List<String> tmp = super._fields();
        tmp.add(FIELD_LANG);
        return tmp;
    }

    public TranslatableDTO(E entity, Locale locale) {
        super(entity, locale);
        this.lang = locale.getLanguage();
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
