package it.ldsoftware.primavera.mapper.base;

import it.ldsoftware.primavera.model.base.AppProperty;
import it.ldsoftware.primavera.presentation.base.AppPropertyDTO;
import org.springframework.stereotype.Service;

/**
 * @author Luca Di Stefano
 */
@Service
public class PropertyMapper extends BaseMapper<AppProperty, AppPropertyDTO> {

    @Override
    public AppProperty getModelInstance(AppPropertyDTO view) {
        AppProperty model = new AppProperty();



        return model;
    }

    @Override
    public AppPropertyDTO getViewInstance(AppProperty model) {
        AppPropertyDTO view = new AppPropertyDTO();

        return view;
    }
}
