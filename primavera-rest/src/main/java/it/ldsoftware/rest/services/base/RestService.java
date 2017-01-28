package it.ldsoftware.rest.services.base;

import it.ldsoftware.primavera.entities.base.BaseEntity;
import it.ldsoftware.primavera.services.interfaces.DatabaseService;
import it.ldsoftware.rest.mapper.base.Mapper;
import it.ldsoftware.rest.payload.base.BasePayload;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This is an abstraction of a REST service.
 * It takes two parameters to tell the autowiring mechanism
 * which kind of classes are involved in this service.<br />
 * <br />
 * Be sure to implement both the {@link Mapper} and that your application's
 * {@link DatabaseService} contains what is necessary to handle operations
 * on the database
 * for the {@link BaseEntity} you're creating the service for
 */
public abstract class RestService<E extends BaseEntity, P extends BasePayload> {

    private Mapper<E, P> mapper;
    private DatabaseService service;

    @Autowired
    public void setMapper(Mapper<E, P> mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setService(DatabaseService service) {
        this.service = service;
    }

    /**
     * Returns the correct mapper for the specified entity-payload pair
     * @return your implementation of the E-P mapper
     */
    public Mapper<E, P> getMapper() {
        return mapper;
    }

    /**
     * Returns the database service to make operations on the database
     * @return your implementation of the database service
     */
    public DatabaseService getService() {
        return service;
    }

}
