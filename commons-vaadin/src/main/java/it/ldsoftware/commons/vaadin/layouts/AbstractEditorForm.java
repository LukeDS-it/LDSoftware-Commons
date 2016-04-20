package it.ldsoftware.commons.vaadin.layouts;

import it.ldsoftware.commons.entities.base.BaseEntity;
import org.vaadin.viritin.layouts.MVerticalLayout;

import java.util.List;

/**
 * This class represents an abstract form for simple editing.
 * It features functions to retrieve and set the entity (bean) that is being edited
 * and on set automatically binds the bean to the form fields (if the names are being respected)
 *
 * @param <E>
 * @author luca
 */
public abstract class AbstractEditorForm<E extends BaseEntity> extends MVerticalLayout {

    private boolean hasNotSavedChanges = false;
    private E bean;

    public boolean hasNotSavedChanges() {
        return hasNotSavedChanges;
    }

    public void resetSaveState() {
        hasNotSavedChanges = false;
    }

    public List<String> validate(Class<?>[] validationGroups) {
        // TODO
        return null;
    }

    public void setBean(E bean) {
        // TODO
        this.bean = bean;
    }

    public E getBean() {
        return bean;
    }

    public abstract void selectFirstField();

}
