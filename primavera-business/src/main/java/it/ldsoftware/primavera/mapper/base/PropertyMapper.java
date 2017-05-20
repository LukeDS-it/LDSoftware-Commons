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

        model.setKey(view.getKey());
        model.setType(view.getPropertyType());
        switch (view.getPropertyType()) {

            case JSON:
                model.setJsonVal(view.getValue().toString());
                break;
            case STRING:
                model.setStringVal(view.getValue().toString());
                break;
            case FLOAT:
                model.setFloatVal((Float) view.getValue());
                break;
            case INTEGER:
                model.setIntVal((Integer) view.getValue());
                break;
            case LONG:
                model.setLongVal((Long) view.getValue());
                break;
            case BOOLEAN:
                model.setBoolVal((Boolean) view.getValue());
                break;
        }

        return model;
    }

    @Override
    public AppPropertyDTO getViewInstance(AppProperty model) {
        AppPropertyDTO view = new AppPropertyDTO();

        view.setKey(model.getKey());
        view.setPropertyType(model.getType());
        view.setValue(model.getRealValue());

        return view;
    }
}
