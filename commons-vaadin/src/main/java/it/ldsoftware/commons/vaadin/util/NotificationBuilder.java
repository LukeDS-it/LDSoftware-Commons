package it.ldsoftware.commons.vaadin.util;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;


import static com.vaadin.shared.Position.BOTTOM_LEFT;
import static com.vaadin.ui.themes.ValoTheme.NOTIFICATION_CLOSABLE;
import static com.vaadin.ui.themes.ValoTheme.NOTIFICATION_SMALL;
import static com.vaadin.ui.themes.ValoTheme.NOTIFICATION_TRAY;

/**
 * Created by luca on 20/04/16.
 * This object builds notifications
 */
public class NotificationBuilder {
    public static void showNotification(String title, String htmlContent, String type) {
        Notification n = new Notification(title);
        n.setHtmlContentAllowed(true);
        n.setStyleName(NOTIFICATION_CLOSABLE
                .concat(" ")
                .concat(NOTIFICATION_TRAY)
                .concat(" ")
                .concat(NOTIFICATION_SMALL)
                .concat(" ")
                .concat(type));
        n.setPosition(BOTTOM_LEFT);
        n.setDelayMsec(5000);
        n.setDescription(htmlContent);
        n.show(Page.getCurrent());
    }
}
