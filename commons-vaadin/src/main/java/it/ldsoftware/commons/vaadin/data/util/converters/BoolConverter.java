package it.ldsoftware.commons.vaadin.data.util.converters;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.Resource;

import java.util.Locale;

import static it.ldsoftware.commons.vaadin.theme.BaseResources.ICON_V;
import static it.ldsoftware.commons.vaadin.theme.BaseResources.ICON_X;
import static it.ldsoftware.commons.vaadin.theme.BaseResources.getIcon;

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
        return (value ? getIcon(ICON_V) : getIcon(ICON_X));
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
