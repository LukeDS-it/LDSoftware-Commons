package it.ldsoftware.primavera.vaadin.data.util.converters;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.Resource;
import it.ldsoftware.primavera.vaadin.theme.BaseResources;

import java.util.Locale;

/**
 * Created by luca on 20/04/16.
 * Converts a two-character locale into a flag
 */
public class FlagConverter implements Converter<Resource, String> {

    @Override
    public String convertToModel(Resource value, Class<? extends String> targetType, Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException {
        return "";
    }

    @Override
    public Resource convertToPresentation(String value, Class<? extends Resource> targetType, Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException {
        return BaseResources.getFlag(value);
    }

    @Override
    public Class<String> getModelType() {
        return String.class;
    }

    @Override
    public Class<Resource> getPresentationType() {
        return Resource.class;
    }

}
