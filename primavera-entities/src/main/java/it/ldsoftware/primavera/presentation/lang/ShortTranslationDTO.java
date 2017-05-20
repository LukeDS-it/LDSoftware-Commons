package it.ldsoftware.primavera.presentation.lang;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Luca Di Stefano
 */
@Getter @Setter
public class ShortTranslationDTO extends TranslationDTO {
    private String description, language;

    public ShortTranslationDTO withContent(String description) {
        this.description = description;
        return this;
    }
}
