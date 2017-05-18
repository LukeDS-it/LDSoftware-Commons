package it.ldsoftware.primavera.services;

import it.ldsoftware.primavera.dal.base.BaseDAL;
import it.ldsoftware.primavera.model.base.BaseEntity;
import it.ldsoftware.primavera.presentation.base.BaseDTO;
import it.ldsoftware.primavera.services.interfaces.BusinessService;

/**
 * Abstract business service with basic implementations. If the business logic
 * does not differ from simple CRUD operations, just leave the implementation alone.
 *
 * Override methods when necessary.
 *
 * @author Luca Di Stefano
 */
public abstract class AbstractBusinessService<D extends BaseDTO> implements BusinessService<D> {

    /**
     * Every Business Service implementation must give back the DAL that manages
     * the entity that it serves.
     *
     * Suggested implementation is
     * <pre>
     *
     *     private final MyEntityDal dal;
     *
     *     {@literal @}Autowired
     *     public MyEntityBusinessService(MyEntityDal dal) {
     *         this.dal = dal;
     *     }
     *
     *     public MyEntityDal getDal() {
     *         return dal;
     *     }
     * </pre>
     *
     * @return an instance of the DAL that allows access to the entity's table
     */
    public abstract BaseDAL getDAL();


}
