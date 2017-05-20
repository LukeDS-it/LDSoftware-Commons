package it.ldsoftware.primavera.textcompiler.services;

import it.ldsoftware.primavera.textcompiler.dto.AbstractModelDTO;
import it.ldsoftware.primavera.textcompiler.dto.CompiledModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author luca
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

    private final ModelService svc;

    @Autowired
    public CompilerService(ModelService svc) {
        this.svc = svc;
    }

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
    public CompiledModel compileModel(AbstractModelDTO model, Locale locale, Object... objects) {
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
        string = replaceLists(string, defaultReplacement, objects);
        string = replacePlaceholders(string, defaultReplacement, objects);
        string = replaceModels(string, defaultReplacement, locale, objects);

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
    public CompiledModel compileModel(AbstractModelDTO model, String defaultReplacement, Locale locale, Object... objects) {
//        AbstractModelTranslation translation = model.getTranslation(locale);
//        return new CompiledModel(
//                compileStringAgainst(translation.getTitle(), defaultReplacement, locale, objects),
//                compileStringAgainst(translation.getBody(), defaultReplacement, locale, objects)
//        );
        return null; // TODO
    }

    /**
     * Replace all the list placeholders in the model.
     * List start is marked by £{ObjectClass[.subProperty].collectionProperty}.
     * List end is marked by £{/}.
     * Anything that is in between list start and list end is the body of the loop
     * and will be repeated for each element in the list.
     *
     * @param string             the model as is
     * @param defaultReplacement the default replacement
     * @param objects            object list
     * @return the model with lists replaced
     */
    private String replaceLists(String string, String defaultReplacement, Object... objects) {
        Pattern listPattern = Pattern.compile("£\\{(.*)\\}");
        Matcher matcher = listPattern.matcher(string);
        int opened = 0;

        while (matcher.find()) {
            if (matcher.group(1).trim().equals("/"))
                opened--;
            else
                opened++;

            if (opened < 0)
                throw new IllegalArgumentException("There is a list closer before a list opener.");
        }
        if (opened > 0)
            throw new IllegalArgumentException("There are more list openers than list closers.");

        while (matcher.find()) {
            Collection<?> collection = null;
            int bodyStart = 0;
            int listStart = 0;
            int bodyEnd;
            int listEnd;

            if (matcher.group(1).trim().equals("/"))
                opened--;
            else
                opened++;

            if (opened == 1) {
                bodyStart = matcher.end();
                listStart = matcher.start();
                collection = (Collection<?>) getPropertyFromObjects(matcher.group(1), objects);
            }
            if (opened == 0) {
                bodyEnd = matcher.start();
                listEnd = matcher.end();
                String allList = string.substring(listStart, listEnd);
                string = string.replace(allList, iterateList(string.substring(bodyStart, bodyEnd), defaultReplacement, collection));
                matcher.reset(string);
            }
        }

        return string;
    }

    private String iterateList(String listBody, String defaultReplacement, Collection<?> elements) {
        String tmp = "";
        for (Object el : elements) {
            tmp += replacePlaceholders(listBody, defaultReplacement, el);
        }
        return tmp;
    }

    /**
     * Replace all the single placeholders in the model.
     * Placeholder is marked as ${ClassName.key[.subProperty[...]]}.
     * <p>
     * Caveat: do not use before list replacement or every sub-key in the list
     * will be substituted with the default replacement string.
     *
     * @param string             model
     * @param defaultReplacement default replacement
     * @param objects            object list
     * @return the model with placeholders replaced.
     */
    private String replacePlaceholders(String string, String defaultReplacement, Object... objects) {
        Pattern placeholderPattern = Pattern.compile("\\$\\{(.*)\\}");
        Matcher matcher = placeholderPattern.matcher(string);
        while (matcher.find()) {
            String property = matcher.group(1).trim();
            Object value = getPropertyFromObjects(property, objects);
            String strValue = defaultReplacement;
            if (value != null)
                strValue = value.toString();
            string = matcher.replaceFirst(strValue);
            matcher.reset(string);
        }
        return string;
    }

    /**
     * Replaces all the models in the model.
     * Models are marked as #{modelNo} with modelNo as a long number
     * that marks the model to be searched for in the database (that is
     * why all the models must stored in the same table).
     * <p>
     * The function replaces the model placeholder with the CONTENT of the
     * model.
     *
     * @param string             model
     * @param defaultReplacement default replacement
     * @param locale             locale to translate the model
     * @param objects            object list
     * @return the model with sub-models replaced.
     */
    private String replaceModels(String string, String defaultReplacement, Locale locale, Object... objects) {
        Matcher matcher;
        Pattern modelPattern = Pattern.compile("#\\{([0-9]*)\\}");
        matcher = modelPattern.matcher(string);
        while (matcher.find()) {
            String modelNo = matcher.group(1).trim();
            CompiledModel cm = compileModel(svc.findOne(Long.parseLong(modelNo)),
                    locale, defaultReplacement, objects);
            String replacement = cm.getBody();
            string = matcher.replaceFirst(replacement);
            matcher.reset(string);
        }
        return string;
    }

    /**
     * Gets the object that matches a certain key from an array of objects
     *
     * @param propertyName the key path (should be ClassName.property1[.property2[...]])
     * @param objects      all the objects that might contain the key
     * @return the object that responds to the key, or null if not found.
     */
    private Object getPropertyFromObjects(String propertyName, Object... objects) {
        String[] propertyPath = propertyName.split("\\.");
        Object match = getObjectMatching(propertyPath[0], objects);

        if (match != null) {
            for (String prop : propertyPath) {
                boolean got = false;
                Class<?> objClass = match.getClass();

                try {
                    Field field = objClass.getField(prop);
                    match = field.get(match);
                    got = true;
                } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ignored) {

                }

                if (!got) {
                    try {
                        Method getter = objClass.getMethod("get" + prop.substring(0, 1).toUpperCase() + prop.substring(1));
                        match = getter.invoke(match);
                        got = true;
                    } catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException | IllegalAccessException ignored) {

                    }
                }

                if (!got) {
                    try {
                        Method getter = objClass.getMethod("is" + prop.substring(0, 1).toUpperCase() + prop.substring(1));
                        match = getter.invoke(match);
                        got = true;
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ignored) {

                    }
                }

                if (!got) {
                    try {
                        Method getter = objClass.getMethod(prop);
                        match = getter.invoke(match);
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ignored) {

                    }
                }
            }
        }
        return match;
    }

    /**
     * Gets the object that matches a given class name from a list of objects
     *
     * @param className the class name
     * @param objects   list of objects
     * @return the object that has the same name as the class name, case ignored, or null if not found
     */
    private Object getObjectMatching(String className, Object... objects) {
        for (Object o : objects) {
            if (o.getClass().getSimpleName().equalsIgnoreCase(className))
                return o;
        }
        return null;
    }

}