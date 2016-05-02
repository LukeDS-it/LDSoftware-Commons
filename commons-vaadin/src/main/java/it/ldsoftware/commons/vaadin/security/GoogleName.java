package it.ldsoftware.commons.vaadin.security;

/**
 * Created by luca on 02/05/16.
 * This object represents the user's real name in the google response
 */
public class GoogleName {
    private String familyName, givenName;

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }
}
