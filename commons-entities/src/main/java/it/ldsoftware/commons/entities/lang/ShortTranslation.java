package it.ldsoftware.commons.entities.lang;

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
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return getContent();
    }

    public ShortTranslation<E> withContent(String content) {
        setContent(content);
        return this;
    }
}
