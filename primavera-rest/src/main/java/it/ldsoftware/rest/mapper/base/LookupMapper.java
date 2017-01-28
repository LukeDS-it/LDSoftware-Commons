package it.ldsoftware.rest.mapper.base;

import it.ldsoftware.primavera.entities.base.Lookup;
import it.ldsoftware.primavera.entities.lang.ShortTranslation;
import it.ldsoftware.rest.mapper.lang.TranslatableMapper;
import it.ldsoftware.rest.payload.base.LookupPayload;
import it.ldsoftware.rest.payload.lang.ShortTranslationPayload;

/**
 * This is an abstract mapper to map lookup data of kind code-description.
 * If your implementation of lookup has additional fields they will
 * be mapped in the initLookup / initLookupPayload methods. If your implementation
 * of the lookup has no additional fields you can leave the implementations empty.
 */
public abstract class LookupMapper<E extends Lookup, P extends LookupPayload>
        extends TranslatableMapper<ShortTranslation, ShortTranslationPayload, E, P> {

    @Override
    public void initEntity(E entity, P payload) {
        super.initEntity(entity, payload);
        initLookup(entity, payload);
        entity.setCode(payload.getCode());
    }

    @Override
    public void initPayload(P payload, E entity) {
        super.initPayload(payload, entity);
        initLookupPayload(payload, entity);
        payload.setCode(entity.getCode());
    }

    /**
     * This method must initialise the specific fields of the lookup.
     * The basic fields will be initialised by the basic mapper
     * @param entity the lookup that is being mapped
     * @param payload the payload that gives the field values
     */
    public abstract void initLookup(E entity, P payload);

    /**
     * This methods must initialise the specific fields of the lookup payload.
     * The basic fields will be initialised by the basic mapper
     * @param payload the payload that is being mapped
     * @param entity the entity that gives the field values
     */
    public abstract void initLookupPayload(P payload, E entity);
}
