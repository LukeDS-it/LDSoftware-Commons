package it.ldsoftware.primavera.entities.lang;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * Created by luca on 12/04/16.
 * Abstract implementation of a short translation,
 * extend this if you need a translation with max 255 characters length
 */
@MappedSuperclass
public abstract class ShortTranslation<E extends Translatable> extends Translation<E> {

    @NotNull
    @Column(nullable = false)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return getDescription();
    }

    public ShortTranslation<E> withContent(String content) {
        setDescription(content);
        return this;
    }
}
