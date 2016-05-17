package it.ldsoftware.primavera.vaadin.editors.security;

import it.ldsoftware.primavera.entities.security.Role;
import it.ldsoftware.primavera.entities.security.RoleTranslation;
import it.ldsoftware.primavera.vaadin.layouts.AbstractEditor;
import it.ldsoftware.primavera.vaadin.layouts.AbstractLookupForm;

/**
 * Created by luca on 03/05/16.
 * Form to create roles
 */
public class RoleForm extends AbstractLookupForm<Role, RoleTranslation> {

    public RoleForm(AbstractEditor parent) {
        super(parent);
    }

    @Override
    public RoleTranslation createTranslation(String text, String lang) {
        return (RoleTranslation) new RoleTranslation().withContent(text).withLang(lang);
    }
}
