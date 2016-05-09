package it.ldsoftware.primavera.vaadin.security;

/**
 * Created by luca on 02/05/16.
 * This object is used for google login and represents the response
 * of the google login service
 */
public class GoogleResponse {

    private GoogleMail[] emails;
    private String id;
    private GoogleName name;
    private GoogleImage image;

    public GoogleMail[] getEmails() {
        return emails;
    }

    public void setEmails(GoogleMail[] emails) {
        this.emails = emails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GoogleName getName() {
        return name;
    }

    public void setName(GoogleName name) {
        this.name = name;
    }

    public GoogleImage getImage() {
        return image;
    }

    public void setImage(GoogleImage image) {
        this.image = image;
    }

    public String getPrincipalMail() {
        return emails[0].getValue();
    }
}
