package it.ldsoftware.primavera.presentation.lang;

import it.ldsoftware.primavera.presentation.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Luca Di Stefano
 */
@Getter @Setter
public class TranslatableDTO<T extends TranslationDTO> extends BaseDTO {
    public static final String FIELD_LANG = "lang", FIELD_MASTER = "master";

    private String defaultLang;
    private Map<String, T> translations = new HashMap<>();

    @Override
    public List<String> _fields() {
        List<String> tmp = super._fields();
        tmp.add(FIELD_LANG);
        return tmp;
    }

    public void addTranslation(String lang, T translationView) {
        translations.put(lang, translationView);
    }
}
