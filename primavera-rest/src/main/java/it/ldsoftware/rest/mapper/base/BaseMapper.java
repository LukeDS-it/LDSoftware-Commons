package it.ldsoftware.rest.mapper.base;

import it.ldsoftware.primavera.entities.base.BaseEntity;
import it.ldsoftware.rest.payload.base.BasePayload;

/**
 * The base mapper is a base implementation of a mapper
 */
public abstract class BaseMapper<E extends BaseEntity, P extends BasePayload> implements Mapper<E, P>{

    @Override
    public P translateEntity(E entity) {
        P payload = newPayload();
        initPayload(payload, entity);
        payload.setId(entity.getId());
        payload.setVersion(entity.getVersion());
        return payload;
    }

    @Override
    public E translatePayload(P payload) {
        E entity = newEntity();
        initEntity(entity, payload);
        entity.setId(payload.getId());
        entity.setVersion(payload.getVersion());
        return entity;
    }

    /**
     * This function must create a new entity
     * @return a new entity of type E
     */
    public abstract E newEntity();

    /**
     * This function must initialise the entity with specific fields.
     * The base mapper will proceed to insert inherited fields.
     * @param entity the entity to initialise
     * @param payload the payload that contains data
     */
    public abstract void initEntity(E entity, P payload);

    /**
     * This function must create a new payload
     * @return a new payload of type P
     */
    public abstract P newPayload();

    /**
     * This function must initialise the payload with specific fields.
     * The base mapper will proceed to insert inherited fields.
     * @param payload the payload to initialise
     * @param entity the entity that comes from the database
     */
    public abstract void initPayload(P payload, E entity);
}
