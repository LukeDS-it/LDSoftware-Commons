package it.ldsoftware.primavera.dto.base;

import it.ldsoftware.primavera.entities.base.BaseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by luca on 11/04/16.
 * This class represents a "view" of the base entity, effectively
 * flattening all meaningful data.
 */
public abstract class BaseDTO<E extends BaseEntity> {

    public static final String FIELD_ID = "id", FIELD_VERSION = "version";

    private long id;

    public BaseDTO() {

    }

    public BaseDTO(E entity) {
        setId(entity.getId());
    }

    public BaseDTO(E entity, Locale locale) {
        this(entity);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof BaseDTO && ((BaseDTO) obj).getId() == getId();
    }

    /**
     * Function used to tell if the DTO is representing a given instance
     * @param entity the instance to be compared with
     * @return true or false
     */

    public boolean sameAs(E entity) {
        return entity != null && entity.getId() == getId();
    }
    /**
     * This function is used to return the list of all field names
     * that will be used in various export functions, e.g. to export
     * CSV files.
     * @return a list of strings
     */
    public List<String> _fields() {
        return Arrays.asList(FIELD_ID);
    }
}
