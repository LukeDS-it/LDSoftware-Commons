package it.ldsoftware.primavera.model.base;

import it.ldsoftware.primavera.util.EntityWithParent;
import it.ldsoftware.primavera.util.ParentEntity;
import it.ldsoftware.primavera.util.PropertyType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

/**
 * Created by luca on 12/04/16.
 *
 * @author luca
 *         This class is used to save on the database
 *         some configurations that can be modified by the application users
 *         and don't belong to a configuration file.
 */
@Entity
@Getter @Setter
@Table(name = "zz_properties")
public class AppProperty extends BaseEntity implements EntityWithParent {

    private String key;
    private Long longVal;
    private Boolean boolVal;
    private Float floatVal;
    private Integer intVal;

    @Column(length = 4096)
    private String jsonVal;

    @Column(length = 512)
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
}
