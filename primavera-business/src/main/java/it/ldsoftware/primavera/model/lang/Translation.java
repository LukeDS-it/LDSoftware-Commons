package it.ldsoftware.primavera.model.lang;

import it.ldsoftware.primavera.model.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Created by luca on 12/04/16.
 * This class represents an abstract translation.
 * There are no fields in this, so each translation must implement
 * the actual database representation. Translation is <b>NOT</b>
 * a {@link BaseEntity}.
 */
@Embeddable
@MappedSuperclass
public abstract class Translation {

    @Transient
    @Getter @Setter
    private String language;

}
