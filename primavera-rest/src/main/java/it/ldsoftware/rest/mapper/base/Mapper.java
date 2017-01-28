package it.ldsoftware.rest.mapper.base;

import it.ldsoftware.primavera.entities.base.BaseEntity;
import it.ldsoftware.rest.payload.base.BasePayload;

/**
 * A mapper is a factory that translates entities coming from the databse
 * into REST payloads and vice versa
 */
public interface Mapper<E extends BaseEntity, P extends BasePayload> {

    P translateEntity(E entity);

    E translatePayload(P payload);

}
