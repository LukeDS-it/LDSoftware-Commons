package it.ldsoftware.primavera.i18n;

/**
 * Created by luca on 18/05/16.
 * Contains useful functions
 */
public abstract class LanguageUtils {

    public static String getTextFieldName(String field) {
        return "txt." + field;
    }

    public static String getCheckboxName(String field) {
        return "chk." + field;
    }

    public static String getCalendarName(String field) {
        return "cal." + field;
    }

    public static String getComboName(String field) {
        return "cmb." + field;
    }

}
