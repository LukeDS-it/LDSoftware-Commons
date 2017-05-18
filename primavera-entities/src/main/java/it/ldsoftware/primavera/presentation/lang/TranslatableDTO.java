package it.ldsoftware.primavera.presentation.lang;

import it.ldsoftware.primavera.presentation.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by luca on 19/05/16.
 *
 */
@Getter @Setter
public class TranslatableDTO extends BaseDTO {
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
}
