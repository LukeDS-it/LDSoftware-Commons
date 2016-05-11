package it.ldsoftware.primavera.entities.base;

import it.ldsoftware.primavera.util.EntityWithParent;
import it.ldsoftware.primavera.util.ParentEntity;

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
@Table(name = "zz_property_group")
public class PropertyGroup extends BaseEntity implements EntityWithParent, ParentEntity {

    private String groupName;

    @ManyToOne
    private PropertyGroup father;

    @OneToMany(cascade = ALL, mappedBy = "group", orphanRemoval = true)
    private Set<AppProperty> properties = new HashSet<>();

    @OneToMany(cascade = ALL, mappedBy = "father", orphanRemoval = true)
    private Set<PropertyGroup> groups = new HashSet<>();

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public PropertyGroup getFather() {
        return father;
    }

    public void setFather(PropertyGroup father) {
        this.father = father;
    }

    public Set<AppProperty> getProperties() {
        return properties;
    }

    public void setProperties(Set<AppProperty> properties) {
        this.properties = properties;
    }

    public Set<PropertyGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<PropertyGroup> groups) {
        this.groups = groups;
    }

    @Override
    public ParentEntity getParent() {
        return father;
    }

    @Override
    public void setParent(ParentEntity parent) {
        father = (PropertyGroup) parent;
    }
}
