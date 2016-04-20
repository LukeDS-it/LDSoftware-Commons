package it.ldsoftware.commons.util;

/**
 * Provides useful methods for reflection
 */
public class ReflectionUtil {
    public static String getPropertyFromMethod(String methodName) {

        if (methodName.startsWith("is")) {
            methodName = methodName.substring(2);
        }
        if (methodName.startsWith("get")) {
            methodName = methodName.substring(3);
        }
        methodName = methodName.substring(0, 1).toLowerCase().concat(methodName.substring(1));

        return methodName;
    }
}
