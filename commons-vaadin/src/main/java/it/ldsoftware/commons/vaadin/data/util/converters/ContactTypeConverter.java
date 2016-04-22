package it.ldsoftware.commons.vaadin.data.util.converters;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.FontAwesome;
import it.ldsoftware.commons.util.ContactType;

import java.util.Locale;

import static com.vaadin.server.FontAwesome.*;

/**
 * Created by luca on 22/04/16.
 * Converts a contact type to the respective HTML representation
 * of the Fontawesome icon
 */
public class ContactTypeConverter implements Converter<String, ContactType> {

    @Override
    public ContactType convertToModel(String value, Class<? extends ContactType> targetType, Locale locale) throws ConversionException {
        return null;
    }

    @Override
    public String convertToPresentation(ContactType value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return findIcon(value).getHtml();
    }

    @Override
    public Class<ContactType> getModelType() {
        return ContactType.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }

    public static FontAwesome findIcon(ContactType value) {
        switch (value) {
            case EMAIL:
                return ENVELOPE_O;
            case FAX:
                return FAX;
            case MOBILE_PHONE:
                return MOBILE_PHONE;
            case CERT_EMAIL:
                return ENVELOPE_SQUARE;
            case PHONE:
                return PHONE;
            case WEBSITE:
                return GLOBE;
        }
        return QUESTION;
    }
}
