package it.ldsoftware.primavera.dto.base;

import it.ldsoftware.primavera.dto.lang.TranslatableDTO;
import it.ldsoftware.primavera.entities.base.Lookup;
import it.ldsoftware.primavera.entities.base.LookupTranslation;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by luca on 12/04/16.
 * DTO that flattens Lookups. Just extend this for your lookups and add
 * other properties if any.
 */
public abstract class LookupDTO<E extends Lookup<? extends LookupTranslation>> extends TranslatableDTO<E> {
    public static final String FIELD_CODE = "code", FIELD_DESCRIPTION = "description";

    private String code, description;

    public LookupDTO(E entity, Locale locale) {
        super(entity, locale);
        setCode(entity.getCode());
        setDescription(entity.getTranslation(locale).getDescription());
    }

    @Override
    public List<String> _fields() {
        List<String> tmp = super._fields();
        tmp.addAll(Arrays.asList(FIELD_CODE, FIELD_DESCRIPTION));
        return tmp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
