package it.ldsoftware.commons.vaadin.editors.security;

import it.ldsoftware.commons.entities.security.Group;
import it.ldsoftware.commons.entities.security.GroupTranslation;
import it.ldsoftware.commons.util.RoleCollector;
import it.ldsoftware.commons.vaadin.layouts.AbstractLookupForm;
import it.ldsoftware.commons.vaadin.util.RoleAdder;

import java.util.Arrays;

import static it.ldsoftware.commons.i18n.CommonLabels.TAB_ROLES;

/**
 * Created by luca on 03/05/16.
 * Form for group editing
 */
public class GroupForm extends AbstractLookupForm<Group, GroupTranslation> implements RoleAdder {

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
