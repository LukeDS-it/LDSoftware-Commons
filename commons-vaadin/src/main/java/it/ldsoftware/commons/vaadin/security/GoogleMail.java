package it.ldsoftware.commons.vaadin.security;

/**
 * Created by luca on 02/05/16.
 * This object represents the email address in the google response
 */
public class GoogleMail {
    private String type, value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
