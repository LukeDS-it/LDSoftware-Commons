package it.ldsoftware.commons.vaadin.data.util.converters;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.Resource;

import java.util.Locale;

import static it.ldsoftware.commons.vaadin.theme.BaseResources.getFlag;

/**
 * Created by luca on 20/04/16.
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
        return getFlag(value);
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
