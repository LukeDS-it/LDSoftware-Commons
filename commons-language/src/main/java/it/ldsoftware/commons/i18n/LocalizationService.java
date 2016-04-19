package it.ldsoftware.commons.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * Created by luca on 19/04/16.
 * This service helps to translate strings from all registered properties files
 */
public class LocalizationService {
    private MessageSource source;
    private Locale locale;

    public LocalizationService(MessageSource msg, Locale locale) {
        source = msg;
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
}
