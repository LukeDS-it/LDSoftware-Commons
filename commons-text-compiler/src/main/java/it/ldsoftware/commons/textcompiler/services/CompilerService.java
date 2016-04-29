package it.ldsoftware.commons.textcompiler.services;

import it.ldsoftware.commons.services.interfaces.DatabaseService;
import it.ldsoftware.commons.textcompiler.dto.CompiledModel;
import it.ldsoftware.commons.textcompiler.entities.AbstractModel;
import it.ldsoftware.commons.textcompiler.entities.AbstractModelTranslation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Luca on 28/04/2016.
 *
 * This service is used to compile models. The primary functions are made to work with any kind of object
 * other than the entities so your project is not tied to use the entity from the database to compile a model
 * and you can add more information to your texts.
 *
 * The class only works with strings, this makes necessary for the
 * class that uses the service to convert from and to desired formats (e.g. pdf, docx, odt...)
 * This minimizes dependencies to avoid forcing a determined file converter over another.
 */
@Service
public class CompilerService {

    @Autowired
    private DatabaseService svc;

    /**
     * This function takes a string and compiles the placeholders over the fields of the elements
     * passed to this function. If a placeholder does not have a corresponding field in all of the
     * objects, it will be replaced with "?" by default.
     * @param string the string that has to be compiled
     * @param locale the desired locale (to allow nested model compilation)
     * @param objects all the objects that contain the fields necessary to compile the string
     * @return the string with the placeholders replaced
     */
    public String compileStringAgainst(String string, Locale locale, Object... objects) {
        return compileStringAgainst(string, "?", locale, objects);
    }

    /**
     * This function takes a model and compiles its content and title against the objects
     * passed to the function. If a placeholder does not have a corresponding field in all of the
     * objects passed, it will be replaced with a "?"
     * @param model the model that has to be compiled
     * @param locale the locale in which to compile the model
     * @param objects all the objects that contain the fields necessary to compile the model
     * @return a compiled model object that contains title and body of the model, compiled in the
     *           desired language
     */
    public CompiledModel compileModel(AbstractModel model, Locale locale, Object... objects) {
        return compileModel(model, "?", locale, objects);
    }

    /**
     * This function takes a string and compiles the placeholders over the fields of the elements
     * passed to this function. If a placeholder does not have a corresponding field in all of the
     * objects, it will be replaced with a predefined placeholder
     * @param string the string that has to be compiled
     * @param defaultReplacement the default string to be used when a field is not found
     * @param locale the desired locale (to allow nested model compilation)
     * @param objects all the objects that contain the fields necessary to compile the string
     * @return the string with the placeholder replaced
     */
    public String compileStringAgainst(String string, String defaultReplacement, Locale locale, Object... objects) {
        Pattern placeholderPattern = Pattern.compile("\\$\\{(.*)\\}");
        Matcher matcher = placeholderPattern.matcher(string);
        while (matcher.matches()) {
            String property = matcher.group(0);
            String value = getPropertyOnObjects(property, objects);
            if (value == null)
                value = defaultReplacement;
            string = matcher.replaceFirst(value);
            matcher.reset();
        }

        Pattern listPattern = Pattern.compile("£\\{(.*)\\}"); // FIXME

        Pattern modelPattern = Pattern.compile("#\\{([0-9]*)\\}");
        matcher = modelPattern.matcher(string);
        while (matcher.matches()) {
            String modelNo = matcher.group(0);
            CompiledModel cm = compileModel(svc.findOne(AbstractModel.class, Long.parseLong(modelNo)),
                    locale, defaultReplacement, objects);
            String replacement = cm.getBody();
            string = matcher.replaceFirst(replacement);
            matcher.reset();
        }
        return string;
    }

    /**
     * This function takes a model and compiles its content and title against the objects
     * passed to the function. If a placeholder does not have a corresponding field in all of the
     * objects passed, it will be replaced with a predefined placeholder
     * @param model the model that has to be compiled
     * @param defaultReplacement the default string to be used when a field is not found
     * @param locale the locale in which to compile the model
     * @param objects all the objects that contain the fields necessary to compile the model
     * @return a compiled model object that contains title and body of the model, ompiled in the
     *          desired language
     */
    public CompiledModel compileModel(AbstractModel model, String defaultReplacement, Locale locale, Object... objects) {
        AbstractModelTranslation translation = model.getTranslation(locale);
        return new CompiledModel(
                compileStringAgainst(translation.getTitle(), defaultReplacement, locale, objects),
                compileStringAgainst(translation.getBody(), defaultReplacement, locale, objects)
        );
    }

    private String getPropertyOnObjects(String propertyName, Object... objects) {
        String s = null;
        // TODO
        return s;
    }

}