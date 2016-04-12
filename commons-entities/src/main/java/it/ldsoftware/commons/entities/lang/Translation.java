package it.ldsoftware.commons.entities.lang;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by luca on 12/04/16.
 * This class represents an abstract translation.
 * There are no fields in this, so each translation must implement
 * the actual database representation
 */
@MappedSuperclass
public abstract class Translation {

    @Column(length = 2, nullable = false)
    private String lang;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
