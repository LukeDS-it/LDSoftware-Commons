package it.ldsoftware.primavera.model.base;

import it.ldsoftware.primavera.util.EntityWithParent;
import it.ldsoftware.primavera.util.ParentEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

/**
 * Created by luca on 11/05/16.
 * This entity groups properties
 */
@Entity
@Getter @Setter
@Table(name = "zz_property_group")
public class PropertyGroup extends BaseEntity implements EntityWithParent, ParentEntity {

    private String groupName;

    @ManyToOne
    private PropertyGroup father;

    @OneToMany(cascade = ALL, mappedBy = "group", orphanRemoval = true)
    private Set<AppProperty> properties = new HashSet<>();

    @OneToMany(cascade = ALL, mappedBy = "father", orphanRemoval = true)
    private Set<PropertyGroup> groups = new HashSet<>();

    @Override
    public ParentEntity getParent() {
        return father;
    }

    @Override
    public void setParent(ParentEntity parent) {
        father = (PropertyGroup) parent;
    }
}
