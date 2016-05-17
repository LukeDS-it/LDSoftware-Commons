package it.ldsoftware.primavera.vaadin.editors.security;

import it.ldsoftware.primavera.entities.security.Group;
import it.ldsoftware.primavera.entities.security.GroupTranslation;
import it.ldsoftware.primavera.util.RoleCollector;
import it.ldsoftware.primavera.vaadin.layouts.AbstractEditor;
import it.ldsoftware.primavera.vaadin.layouts.AbstractLookupForm;
import it.ldsoftware.primavera.vaadin.util.RoleAdder;

import java.util.Arrays;

import static it.ldsoftware.primavera.i18n.CommonLabels.TAB_ROLES;

/**
 * Created by luca on 03/05/16.
 * Form for group editing
 */
public class GroupForm extends AbstractLookupForm<Group, GroupTranslation> implements RoleAdder {

    public GroupForm(AbstractEditor parent) {
        super(parent);
    }

    @Override
    public void addOtherTabs() {
        super.addOtherTabs();
        RolesDetailTab roles = new RolesDetailTab(getTranslator(), getDatabaseService(), this);
        addTab(roles, getTranslator().translate(TAB_ROLES));
    }

    @Override
    public GroupTranslation createTranslation(String text, String lang) {
        return (GroupTranslation) new GroupTranslation().withContent(text).withLang(lang);
    }

    @Override
    public void addRoles(RoleCollector... roles) {
        Arrays.stream(roles).forEach(getBean()::addRole);
    }

    @Override
    public void remRoles(RoleCollector... roles) {
        Arrays.stream(roles).forEach(getBean()::remRole);
    }
}
