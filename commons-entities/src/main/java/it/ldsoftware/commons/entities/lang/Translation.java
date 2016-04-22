package it.ldsoftware.commons.entities.lang;

import it.ldsoftware.commons.entities.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.EAGER;

/**
 * Created by luca on 12/04/16.
 * This class represents an abstract translation.
 * There are no fields in this, so each translation must implement
 * the actual database representation
 */
@MappedSuperclass
public abstract class Translation<E extends Translatable> extends BaseEntity {

    @Column(length = 2, nullable = false)
    private String lang;

    @ManyToOne(fetch = EAGER)
    private E master;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public E getMaster() {
        return master;
    }

    public void setMaster(E master) {
        this.master = master;
    }

    public Translation<E> withLang(String lang) {
        setLang(lang);
        return this;
    }
}
