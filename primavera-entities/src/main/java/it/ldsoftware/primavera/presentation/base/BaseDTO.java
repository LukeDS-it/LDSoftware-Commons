package it.ldsoftware.primavera.presentation.base;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * Created by luca on 11/04/16.
 * This class represents a "view" of the base entity, effectively
 * flattening all meaningful data.
 */
@Getter @Setter
public abstract class BaseDTO {

    public static final String FIELD_ID = "id", FIELD_VERSION = "version";

    private long id;

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof BaseDTO && ((BaseDTO) obj).getId() == getId();
    }

    /**
     * This function is used to return the list of all field names
     * that will be used in various export functions, e.g. to export
     * CSV files.
     * @return a list of strings
     */
    public List<String> _fields() {
        return Collections.singletonList(FIELD_ID);
    }
}
