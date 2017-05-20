package it.ldsoftware.primavera.presentation.base;

import it.ldsoftware.primavera.presentation.lang.ShortTranslationDTO;
import it.ldsoftware.primavera.presentation.lang.TranslatableDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by luca on 12/04/16.
 * DTO that flattens Lookups. Just extend this for your lookups and add
 * other properties if any.
 */
@Getter @Setter
public abstract class LookupDTO extends TranslatableDTO<ShortTranslationDTO> {
    public static final String FIELD_CODE = "code", FIELD_DESCRIPTION = "description";

    private String code, description;

    @Override
    public List<String> _fields() {
        List<String> tmp = super._fields();
        tmp.addAll(Arrays.asList(FIELD_CODE, FIELD_DESCRIPTION));
        return tmp;
    }

}
