package it.ldsoftware.rest.payload.lang;

public class ShortTranslationPayload extends TranslationPayload {
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ShortTranslationPayload withDescription(String description) {
        setDescription(description);
        return this;
    }
}
