package it.ldsoftware.primavera.vaadin.data;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import it.ldsoftware.primavera.entities.base.AppProperty;
import it.ldsoftware.primavera.entities.base.PropertyGroup;
import it.ldsoftware.primavera.entities.base.QAppProperty;
import it.ldsoftware.primavera.entities.base.QPropertyGroup;
import it.ldsoftware.primavera.services.interfaces.DatabaseService;
import it.ldsoftware.primavera.util.EntityWithParent;
import it.ldsoftware.primavera.util.ParentEntity;
import org.hibernate.LazyInitializationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toSet;

/**
 * Created by luca on 11/05/16.
 * This container is used to host property groups and properties.
 */
public class AppPropertyContainer implements Container.Hierarchical {

    private List<PropertyGroup> rootGroups = null;
    private DatabaseService svc;

    public AppPropertyContainer(DatabaseService service) {
        svc = service;
    }

    @Override
    public Collection<?> getChildren(Object itemId) {
        List<EntityWithParent> entities = new ArrayList<>();
        PropertyGroup group = (PropertyGroup) itemId;
        try {
            group.getGroups().forEach(entities::add);
        } catch (LazyInitializationException ex) {
            initGroups(group);
            group.getGroups().forEach(entities::add);
        }
        try {
            group.getProperties().forEach(entities::add);
        } catch (LazyInitializationException ex) {
            initProperties(group);
            group.getProperties().forEach(entities::add);
        }
        return entities;
    }

    @Override
    public Object getParent(Object itemId) {
        if (itemId instanceof EntityWithParent) {
            try {
                return ((EntityWithParent) itemId).getParent();
            } catch (LazyInitializationException ex) {
                QPropertyGroup qPg = QPropertyGroup.propertyGroup;
                PropertyGroup pg = svc.findOne(PropertyGroup.class, qPg.groups.contains((PropertyGroup) itemId));
                ((PropertyGroup) itemId).setParent(pg);
                return pg;
            }
        }
        return null;
    }

    @Override
    public Collection<?> rootItemIds() {
        if (rootGroups == null)
            rootGroups = svc.findAll(PropertyGroup.class);
        return rootGroups;
    }

    @Override
    public boolean setParent(Object itemId, Object newParentId) throws UnsupportedOperationException {
        if (itemId instanceof EntityWithParent && newParentId instanceof ParentEntity) {
            ((EntityWithParent) itemId).setParent((ParentEntity) newParentId);
            // TODO save the entity
            return true;
        }
        return false;
    }

    @Override
    public boolean areChildrenAllowed(Object itemId) {
        return itemId instanceof PropertyGroup;
    }

    @Override
    public boolean isRoot(Object itemId) {
        return !(itemId instanceof AppProperty) && ((PropertyGroup) itemId).getFather() == null;
    }

    @Override
    public boolean hasChildren(Object itemId) {
        boolean hasChildren = false;
        if (itemId instanceof PropertyGroup) {
            PropertyGroup group = (PropertyGroup) itemId;
            try {
                hasChildren &= !group.getGroups().isEmpty();
            } catch (LazyInitializationException ex) {
                initGroups(group);
                hasChildren &= !group.getGroups().isEmpty();
            }
            try {
                hasChildren &= !group.getProperties().isEmpty();
            } catch (LazyInitializationException ex) {
                initProperties(group);
                hasChildren &= !group.getProperties().isEmpty();
            }
        }
        return hasChildren;
    }

    @Override
    public Item getItem(Object itemId) {
        return null; // TODO
    }

    @Override
    public Collection<?> getContainerPropertyIds() {
        return null; // TODO
    }

    @Override
    public Collection<?> getItemIds() {
        return null; // TODO
    }

    @Override
    public Property getContainerProperty(Object itemId, Object propertyId) {
        return null; // TODO
    }

    @Override
    public Class<?> getType(Object propertyId) {
        return null; // TODO
    }

    @Override
    public int size() {
        return 0; // TODO
    }

    @Override
    public boolean containsId(Object itemId) {
        return false; // TODO
    }

    @Override
    public Item addItem(Object itemId) throws UnsupportedOperationException {
        return null; // TODO
    }

    @Override
    public boolean removeItem(Object itemId) throws UnsupportedOperationException {
        return false; // TODO
    }

    @Override
    public boolean addContainerProperty(Object propertyId, Class<?> type, Object defaultValue) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeContainerProperty(Object propertyId) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAllItems() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Items cannot be removed in order to avoid configuration errors");
    }

    @Override
    public Object addItem() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Adding items is permitted only by passing the item");
    }

    @Override
    public boolean setChildrenAllowed(Object itemId, boolean areChildrenAllowed) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This hierarchy is not modifiable!");
    }

    private void initProperties(PropertyGroup group) {
        QAppProperty qAp = QAppProperty.appProperty;
        group.setProperties(svc.findAll(AppProperty.class, qAp.group.eq(group)).stream().collect(toSet()));
    }

    private void initGroups(PropertyGroup group) {
        QPropertyGroup qPg = QPropertyGroup.propertyGroup;
        group.setGroups(svc.findAll(PropertyGroup.class, qPg.father.eq(group)).stream().collect(toSet()));
    }
}
