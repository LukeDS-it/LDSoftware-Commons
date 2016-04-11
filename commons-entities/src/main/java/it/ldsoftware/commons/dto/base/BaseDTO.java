package it.ldsoftware.commons.dto.base;

import it.ldsoftware.commons.entities.base.BaseEntity;

import java.util.Locale;

/**
 * Created by luca on 11/04/16.
 * This class represents a "view" of the base entity, effectively
 * flattening all meaningful data
 */
public abstract class BaseDTO<E extends BaseEntity> {

    private long id;

    public BaseDTO() {

    }

    public BaseDTO(E entity) {
        setId(entity.getId());
    }

    public BaseDTO(E entity, Locale locale) {
        this(entity);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof BaseDTO && ((BaseDTO) obj).getId() == getId();
    }

    public boolean sameAs(E entity) {
        return entity != null && entity.getId() == getId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
