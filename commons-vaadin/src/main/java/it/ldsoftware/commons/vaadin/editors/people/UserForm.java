package it.ldsoftware.commons.vaadin.editors.people;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import it.ldsoftware.commons.dto.security.GroupDTO;
import it.ldsoftware.commons.dto.security.UserRoleDTO;
import it.ldsoftware.commons.entities.people.User;
import it.ldsoftware.commons.entities.security.Group;
import it.ldsoftware.commons.util.PersonType;
import it.ldsoftware.commons.util.RoleCollector;
import it.ldsoftware.commons.vaadin.editors.security.GroupsDetailTab;
import it.ldsoftware.commons.vaadin.editors.security.RolesDetailTab;
import it.ldsoftware.commons.vaadin.layouts.AbstractPersonForm;
import it.ldsoftware.commons.vaadin.util.GroupAdder;
import it.ldsoftware.commons.vaadin.util.RoleAdder;
import org.vaadin.viritin.fields.MPasswordField;
import org.vaadin.viritin.fields.MTextField;

import static it.ldsoftware.commons.i18n.CommonLabels.*;
import static it.ldsoftware.commons.vaadin.theme.MetricConstants.FIELD_WIDTH;
import static java.util.stream.Collectors.toList;

/**
 * Created by luca on 22/04/16.
 * Form for the user
 */
public class UserForm extends AbstractPersonForm<User> implements RoleAdder, GroupAdder {

    private RolesDetailTab rolesDetailTab;
    private GroupsDetailTab groupsTab;

    private TextField username, primaryEmail;
    private PasswordField password, confirmPassword;
    private CheckBox enabled;

    @Override
    public void addPersonalFields(VerticalLayout generalTab) {
        switchFields(PersonType.REAL);

        username = new MTextField(getTranslator().translate(getTextFieldName("username"))).withWidth(FIELD_WIDTH).withNullRepresentation("");
        password = new MPasswordField(getTranslator().translate(getTextFieldName("password"))).withWidth(FIELD_WIDTH).withNullRepresentation("");
        confirmPassword = new MPasswordField(getTranslator().translate(getTextFieldName("confirmPassword"))).withWidth(FIELD_WIDTH).withNullRepresentation("");
        enabled = new CheckBox(getTranslator().translate(getCheckboxName("enabled")));
        primaryEmail = new MTextField(getTranslator().translate(getTextFieldName("primaryEmail"))).withWidth(FIELD_WIDTH).withNullRepresentation("");

        generalTab.addComponentAsFirst(primaryEmail);
        generalTab.addComponentAsFirst(confirmPassword);
        generalTab.addComponentAsFirst(password);
        generalTab.addComponentAsFirst(username);
        generalTab.addComponent(enabled);

        wireEvents();
    }

    @Override
    public void addOtherTabs() {
        super.addOtherTabs();
        addRolesTab();
        addGroupsTab();
    }

    @Override
    public void addRoles(RoleCollector... roles) {
        // TODO
    }

    @Override
    public void remRoles(RoleCollector... roles) {
        // TODO
    }

    @Override
    public void addGroups(Group... groups) {
        // TODO
    }

    @Override
    public void remGroups(Group... groups) {
        // TODO
    }

    @Override
    public User findPerson(long id) {
        return getDatabaseService().findOne(User.class, id);
    }

    @Override
    public void setBean(User bean) {
        super.setBean(bean);
        rolesDetailTab.refreshGrid(bean.getUserRoles().stream()
                .map(r -> new UserRoleDTO(r, getLocale())).collect(toList()));
        groupsTab.refreshGrid(bean.getGroups().stream()
                .map(g -> new GroupDTO(g, getLocale())).collect(toList()));
    }

    private void addRolesTab() {
        rolesDetailTab = new RolesDetailTab(getTranslator(), getDatabaseService(), this);
        addTab(rolesDetailTab, getTranslator().translate(TAB_ROLES));
    }

    private void addGroupsTab() {
        groupsTab = new GroupsDetailTab(getTranslator(), getDatabaseService(), this);
        addTab(groupsTab, getTranslator().translate(TAB_GROUPS));
    }

    private void wireEvents() {
        username.addValueChangeListener(l -> signalChange());
        password.addValueChangeListener(l -> signalChange());
        confirmPassword.addValueChangeListener(l -> signalChange());
        enabled.addValueChangeListener(l -> signalChange());
        primaryEmail.addValueChangeListener(l -> signalChange());
    }

    boolean isChangedPassword() {
        return !password.isEmpty() || getBean().getId() == 0;
    }
}
