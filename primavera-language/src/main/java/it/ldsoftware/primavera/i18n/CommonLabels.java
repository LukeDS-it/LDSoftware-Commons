package it.ldsoftware.primavera.i18n;

/**
 * Created by luca on 19/04/16.
 * This class contains all frequently used labels
 */
public class CommonLabels {
    public static final String YES = "yes", NO = "no", TXT_FILTER = "txt.filter",
            CANCEL = "cancel", USERNAME = "username", PASSWORD = "password", ADD = "add",
            DEFAULT = "default";

    public static final String BTN_LOGIN = "btn.login", BTN_GOOGLE_LOGIN = "btn.google.login",
            BTN_TWITTER_LOGIN = "btn.twitter.login", BTN_FACEBOOK_LOGIN = "btn.facebook.login";

    public static final String ERROR_CREDENTIALS_EXPIRED = "error.credentials.expired",
            ERROR_BAD_CREDENTIALS = "error.bad.credentials", ERROR_DISABLED = "error.disabled",
            ERROR_LOGIN_ACCOUNT = "error.login.account", ERROR_LOGIN_GENERIC = "error.login.generic",
            ERROR_GOOGLE_AUTH_CONFIG = "error.google.auth.config";

    public static final String WARNING_SELECT_ITEMS = "warning.select.items";

    public static final String LABEL_LOGIN = "label.login", LABEL_ADD_ROLE = "label.add.role",
            LABEL_ADD_GROUP = "label.add.group";

    public static final String BTN_ADD = "btn.add";

    public static final String TITLE_CONFIRM_NOT_SAVED = "title.confirm.not.saved",
            TITLE_CONFIRM_DELETE = "title.confirm.delete", TITLE_SAVE_SUCCESS = "title.save.success",
            TITLE_TOS = "title.tos", TITLE_GENERIC_WARNING = "title.generic.warning",
            TITLE_SAVE_ERROR = "title.save.error", TITLE_SELECT_ELEMENTS = "title.select.elements";

    public static final String TOOLTIP_REFRESH = "tooltip.refresh";

    public static final String MSG_CONFIRM_NOT_SAVED = "msg.confirm.not.saved",
            MSG_CONFIRM_DELETE_SINGLE = "msg.confirm.delete.single",
            MSG_CONFIRM_DELETE_MULTI = "msg.confirm.delete.multi",
            MSG_SAVE_SUCCESS = "msg.save.success",
            MSG_TERM_SERVICE = "msg.term.service", MSG_SAVE_ERROR = "msg.save.error",
            MSG_INS_ERROR = "msg.ins.error";

    public static final String EDITOR_ROLES = "editor.roles",
    EDITOR_GROUPS = "editor.groups", EDITOR_PEOPLE = "editor.people",
    EDITOR_USERS = "editor.user";

    public static final String TAB_ROLES = "tab.roles", TAB_GROUPS = "tab.groups";

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
