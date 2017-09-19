package it.ldsoftware.primavera.model.base;

import it.ldsoftware.primavera.presentation.enums.PropertyType;
import it.ldsoftware.primavera.util.EntityWithParent;
import it.ldsoftware.primavera.util.ParentEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

/**
 * <p>
 * This class is used to save on the database
 * some configurations that can be modified by the application users
 * and don't belong to a configuration file.
 *
 * @author Luca Di Stefano
 */
@Entity
@Getter @Setter
@Table(name = "zz_properties")
public class AppProperty extends BaseEntity implements EntityWithParent {

    private String key;

    @Column(name = "long_val")
    private Long longVal;

    @Column(name = "bool_val")
    private Boolean boolVal;

    @Column(name = "float_val")
    private Float floatVal;

    @Column(name = "int_val")
    private Integer intVal;

    @Column(length = 4096, name = "json_val")
    private String jsonVal;

    @Column(length = 512, name = "string_val")
    private String stringVal;

    @Enumerated(STRING)
    private PropertyType type;

    @ManyToOne
    private PropertyGroup group;

    @Override
    public ParentEntity getParent() {
        return group;
    }

    @Override
    public void setParent(ParentEntity parent) {
        group = (PropertyGroup) parent;
    }

    public Object getRealValue() {
        switch (type) {
            case JSON:
                return jsonVal;
            case STRING:
                return stringVal;
            case FLOAT:
                return floatVal;
            case INTEGER:
                return intVal;
            case LONG:
                return longVal;
            case BOOLEAN:
                return boolVal;
        }
        return null;
    }
}
