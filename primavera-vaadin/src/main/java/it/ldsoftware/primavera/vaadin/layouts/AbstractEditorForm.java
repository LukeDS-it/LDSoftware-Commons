package it.ldsoftware.primavera.vaadin.layouts;

import it.ldsoftware.primavera.model.base.BaseEntity;
import it.ldsoftware.primavera.i18n.LocalizationService;
import it.ldsoftware.primavera.services.interfaces.DatabaseService;
import org.vaadin.viritin.BeanBinder;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    private AbstractEditor<?, ?> parentLayout;

    private static ValidatorFactory factory;
    private transient Validator javaxBeanValidator;

    public AbstractEditorForm(AbstractEditor parentLayout) {
        this.parentLayout = parentLayout;
        setMargin(false);
        setSizeFull();
    }

    List<String> validate(Class<?>[] validationGroups) {
        List<String> errors = new ArrayList<>();

        if (factory == null) {
            factory = Validation.buildDefaultValidatorFactory();
        }
        if (javaxBeanValidator == null) {
            javaxBeanValidator = factory.getValidator();
        }

        Set<ConstraintViolation<E>> violations = javaxBeanValidator.validate(bean, validationGroups);

        if (violations != null && !violations.isEmpty()) {
            violations
                    .stream()
                    .map(cv -> getTranslator().getConstraintViolation(cv))
                    .forEach(errors::add);
        }

        return errors;
    }

    public void setBean(E bean) {
        this.bean = bean;
        BeanBinder.bind(bean, this);
    }

    public E getBean() {
        return bean;
    }

    public abstract void selectFirstField();

    public void signalChange() {
        hasNotSavedChanges = true;
    }

    boolean hasNotSavedChanges() {
        return hasNotSavedChanges;
    }

    void resetSaveState() {
        hasNotSavedChanges = false;
    }

    public DatabaseService getDatabaseService() {
        return parentLayout.getSvc();
    }

    public LocalizationService getTranslator() {
        return parentLayout.getTranslator();
    }

    public AbstractEditor<?, ?> getEditor() {
        return parentLayout;
    }

    void setParentLayout(AbstractEditor<?, ?> parentLayout) {
        this.parentLayout = parentLayout;
    }

    public Validator getBeanValidator() {
        return javaxBeanValidator;
    }
}
