package it.ldsoftware.commons.textcompiler.entities;

import it.ldsoftware.commons.entities.lang.Translation;

import javax.persistence.*;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

/**
 * Created by Luca on 28/04/2016.
 * A text is basically composed by a title and the body of the text that
 * can be a long formatted text (the body) such as a rich text or an HTML, an XML, etc.
 */
@Entity
@Table(name = "zz_model_translation")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "text_type")
public class AbstractModelTranslation extends Translation<AbstractModel> {

    private String title;

    @Lob
    private String body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
