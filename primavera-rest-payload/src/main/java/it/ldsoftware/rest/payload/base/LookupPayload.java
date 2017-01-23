package it.ldsoftware.rest.payload.base;

/**
 * Payload for a generic lookup containing code and description
 */
public class LookupPayload extends BasePayload {

    private String name, description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
