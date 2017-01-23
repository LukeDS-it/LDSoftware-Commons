package it.ldsoftware.rest.payload.base;

import java.util.Set;

/**
 * This payload specifies a property group
 */
public class PropertyGroupPayload {

    private String groupName;
    private Set<PropertyPayload> properties;
    private Set<PropertyGroupPayload> children;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Set<PropertyPayload> getProperties() {
        return properties;
    }

    public void setProperties(Set<PropertyPayload> properties) {
        this.properties = properties;
    }

    public Set<PropertyGroupPayload> getChildren() {
        return children;
    }

    public void setChildren(Set<PropertyGroupPayload> children) {
        this.children = children;
    }
}
