package it.ldsoftware.commons.entities.base;

import it.ldsoftware.commons.entities.lang.Translatable;

import javax.persistence.MappedSuperclass;

/**
 * Created by luca on 12/04/16.
 * This class describes a basic lookup table, i.e.
 * an entity that has a "code" and a multi-language "description",
 * e.g. the metrical units can be a lookup in the way that the code
 * equals the symbol of the unit (m), and the description the actual
 * definition (en = metres, it = metri)
 */
@MappedSuperclass
public abstract class Lookup<T extends LookupTranslation> extends Translatable<T> {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
