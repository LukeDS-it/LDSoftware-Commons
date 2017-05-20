package it.ldsoftware.primavera.services;

import it.ldsoftware.primavera.dal.base.BaseDAL;
import it.ldsoftware.primavera.mapper.Mapper;
import it.ldsoftware.primavera.model.base.AppProperty;
import it.ldsoftware.primavera.presentation.base.AppPropertyDTO;
import it.ldsoftware.primavera.services.interfaces.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Luca Di Stefano
 */
@Service
public class PropertyBusinessService extends AbstractBusinessService<AppPropertyDTO, AppProperty> implements BusinessService<AppPropertyDTO> {

    @Autowired
    public PropertyBusinessService(BaseDAL<AppProperty> dal, Mapper<AppProperty, AppPropertyDTO> mapper) {
        super(dal, mapper);
    }

}
