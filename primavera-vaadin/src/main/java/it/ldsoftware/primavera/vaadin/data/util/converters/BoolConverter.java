package it.ldsoftware.primavera.vaadin.data.util.converters;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.Resource;
import it.ldsoftware.primavera.vaadin.theme.BaseResources;

import java.util.Locale;

/**
 * Created by luca on 22/04/16.
 * Converts boolean values into images (tick / x)
 */
public class BoolConverter implements Converter<Resource, Boolean> {

    @Override
    public Boolean convertToModel(Resource value, Class<? extends Boolean> targetType, Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException {
        return null;
    }

    @Override
    public Resource convertToPresentation(Boolean value, Class<? extends Resource> targetType, Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException {
        return (value ? BaseResources.getIcon(BaseResources.ICON_V) : BaseResources.getIcon(BaseResources.ICON_X));
    }

    @Override
    public Class<Boolean> getModelType() {
        return Boolean.class;
    }

    @Override
    public Class<Resource> getPresentationType() {
        return Resource.class;
    }
}
