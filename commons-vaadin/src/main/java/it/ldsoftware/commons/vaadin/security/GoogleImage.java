package it.ldsoftware.commons.vaadin.security;

/**
 * Created by luca on 02/05/16.
 * This class represents the profile picture of the user
 * in the google response
 */
public class GoogleImage {
    private String url;
    private boolean isDefault;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

}
