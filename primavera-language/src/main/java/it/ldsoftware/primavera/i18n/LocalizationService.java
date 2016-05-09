package it.ldsoftware.primavera.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.text.MessageFormat;
import java.util.Locale;

/**
 * Created by luca on 19/04/16.
 * This service helps to translate strings from all registered properties files
 */
@Service
public class LocalizationService {
    @Autowired
    private MessageSource source;
    private Locale locale;

    private static final String[] VARIETIES = {"txt.", "cmb.", "cal.", "sel."};

    public LocalizationService(MessageSource msg, Locale locale) {
        source = msg;
        this.locale = locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String translate(String label) {
        return getMessage(label);
    }

    public String translate(String label, Object... arguments) {
        return getMessage(label, arguments);
    }

    private String getMessage(String key, Object... arguments) {
        try {
            String pattern = source.getMessage(key, arguments, locale);
            if (pattern.matches("\\$\\{.*\\}"))
                pattern = getMessage(pattern.substring(2, pattern.length() - 1), arguments);

            final MessageFormat format = new MessageFormat(pattern, locale);
            return format.format(arguments);
        } catch (NoSuchMessageException ex) {
            return key;
        }
    }

    public String getConstraintViolation(ConstraintViolation<?> cv) {
        String constraintError = cv.getMessageTemplate().substring(1, cv.getMessageTemplate().length() - 1);
        String field = getFieldVariety(cv.getPropertyPath().toString(), locale);
        return getMessage(constraintError, field);
    }

    private String getFieldVariety(String fieldName, Object... arguments) {
        for (String variety : VARIETIES) {
            try {
                return searchMessage(variety + fieldName, arguments);
            } catch (NoSuchMessageException ignored) {

            }
        }
        return fieldName;
    }

    private String searchMessage(String key, Object... arguments) {
        String pattern = source.getMessage(key, arguments, locale);
        if (pattern.matches("\\$\\{.*\\}"))
            pattern = searchMessage(pattern.substring(2, pattern.length() - 1), arguments);

        final MessageFormat format = new MessageFormat(pattern, locale);
        return format.format(arguments);
    }
}
