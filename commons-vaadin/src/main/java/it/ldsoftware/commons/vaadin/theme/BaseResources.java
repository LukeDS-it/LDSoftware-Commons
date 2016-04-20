package it.ldsoftware.commons.vaadin.theme;

import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;

import java.util.Locale;

/**
 * Created by luca on 20/04/16.
 * This class contains all base resources of the project
 */
public class BaseResources {

    public final static String ICON_V = "v.png", ICON_X = "x.png", ICON_CONTACTCARD = "contactcard.png",
            ICON_USER = "user.png", ICON_GROUP = "group.png", ICON_TRASH = "trash.png", ICON_GIS = "gis.png",
            ICON_EMPTY = "empty.png";

    public final static String LOGO_DP_XS = "dp_xs.jpg";

    public final static String FULL_ICON_CONTACTCARD = "../vaadinbase/images/icons/contactcard.png",
            FULL_ICON_GEARS = "../vaadinbase/images/icons/settings.png",
            FULL_ICON_USER = "../vaadinbase/images/icons/user.png",
            FULL_ICON_GROUP = "../vaadinbase/images/icons/group.png",
            FULL_ICON_DATABASE = "../vaadinbase/images/icons/database.png";

    private final static String PATH_BASE = "../vaadinbase/",
            PATH_IMAGES = PATH_BASE.concat("images/"),
            PATH_FLAGS = PATH_IMAGES.concat("flags"),
            PATH_ICONS = PATH_IMAGES.concat("icons/"),
            PATH_LOGOS = PATH_IMAGES.concat("logos/");

    public static Resource getFlag(Locale locale) {
        return getFlag(locale.getLanguage());
    }

    public static Resource getFlag(String lang) {
        return new ThemeResource(PATH_FLAGS.concat("/").concat(lang).concat(".png"));
    }

    public static Resource getIcon(String icon) {
        return new ThemeResource(getIconPath(icon));
    }

    public static Resource getLogo(String logo) {
        return new ThemeResource(getLogoPath(logo));
    }

    public static String getIconPath(String icon) {
        return PATH_ICONS.concat(icon);
    }

    public static String getLogoPath(String logo) {
        return PATH_LOGOS.concat(logo);
    }
}
