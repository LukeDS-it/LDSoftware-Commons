package it.ldsoftware.primavera.textcompiler.entities;

import it.ldsoftware.primavera.model.lang.Translation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

/**
 * Created by Luca on 28/04/2016.
 * A text is basically composed by a title and the body of the text that
 * can be a long formatted text (the body) such as a rich text or an HTML, an XML, etc.
 */
@Entity
@Getter @Setter
@Table(name = "zz_model_translation")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "text_type")
public class AbstractModelTranslation extends Translation {

    private String title;

    @Lob
    private String body;

}
