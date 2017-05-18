package it.ldsoftware.primavera.model.lang;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * Created by luca on 12/04/16.
 * Abstract implementation of a short translation,
 * extend this if you need a translation with max 255 characters length
 */
@Embeddable
@Getter @Setter
public class ShortTranslation extends Translation {

    @NotNull
    @Column(nullable = false)
    private String description;

    @Override
    public String toString() {
        return description;
    }

    public ShortTranslation withContent(String content) {
        this.description = content;
        return this;
    }
}
