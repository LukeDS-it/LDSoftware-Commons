package it.ldsoftware.primavera.vaadin.grouping;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static it.ldsoftware.primavera.util.UserUtil.ROLE_ANONYMOUS;

/**
 * Created by luca on 16/05/16.
 *
 * The GUI Gear is an annotation that can be used to group objects of kind
 * {@link com.vaadin.ui.Component} and {@link com.vaadin.ui.Component.Listener}.
 *
 * Along with the {@link ComponentFactory}, groups of clickable elements that
 * will either attach components to the UI or trigger a listener action.
 */
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GuiGear {

    /**
     * Specifies the caption of the element.
     * Can be the actual string to display or a label that will be interpreted
     * by the {@link it.ldsoftware.primavera.i18n.LocalizationService}.
     * Can be null, in which case the caption will be an empty string.
     * @return caption or placeholder
     */
    String caption() default "";

    /**
     * Specifies the role name that the user must have to see the element
     * @return string with role name
     */
    String role() default ROLE_ANONYMOUS;

    /**
     * Specifies additional CSS styles to be added to the final component
     * @return a list of css classes as they would appear in an HTML tag
     */
    String css() default "";

    /**
     * Specifies the group that contains the element to enable filtering
     * @return an user-defined string with a group name.
     */
    String group();

}
