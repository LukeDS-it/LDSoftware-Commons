package it.ldsoftware.primavera.services;

import it.ldsoftware.primavera.dal.base.AppPropertyDAL;
import it.ldsoftware.primavera.dal.base.BaseDAL;
import it.ldsoftware.primavera.mapper.Mapper;
import it.ldsoftware.primavera.model.base.AppProperty;
import it.ldsoftware.primavera.presentation.base.AppPropertyDTO;
import it.ldsoftware.primavera.services.interfaces.BusinessService;
import it.ldsoftware.primavera.services.interfaces.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Luca Di Stefano
 */
@Service
public class PropertyBusinessService extends AbstractBusinessService<AppPropertyDTO, AppProperty> implements PropertyService {

    @Autowired
    public PropertyBusinessService(BaseDAL<AppProperty> dal, Mapper<AppProperty, AppPropertyDTO> mapper) {
        super(dal, mapper);
    }

    @Override
    public AppPropertyDTO findByKey(String key) {
        return getMapper().convertToView(((AppPropertyDAL) getDal()).findByKey(key));
    }
}
