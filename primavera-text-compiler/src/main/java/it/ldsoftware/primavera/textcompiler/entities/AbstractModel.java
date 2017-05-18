package it.ldsoftware.primavera.textcompiler.entities;

import it.ldsoftware.primavera.model.lang.Translatable;

import javax.persistence.*;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

/**
 * Created by Luca on 28/04/2016.
 * Extend this class to create your instance of text, giving the discriminator
 * (texts will go in the same table, zz_models)
 */

@Entity
@Table(name = "zz_models")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "text_type")
public class AbstractModel extends Translatable<AbstractModelTranslation> {

}
