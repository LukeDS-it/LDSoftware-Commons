package it.ldsoftware.primavera.vaadin.editors.people;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import it.ldsoftware.primavera.presentation.security.GroupDTO;
import it.ldsoftware.primavera.presentation.security.UserRoleDTO;
import it.ldsoftware.primavera.model.people.User;
import it.ldsoftware.primavera.model.security.Group;
import it.ldsoftware.primavera.util.PersonType;
import it.ldsoftware.primavera.util.RoleCollector;
import it.ldsoftware.primavera.vaadin.editors.security.GroupsDetailTab;
import it.ldsoftware.primavera.vaadin.editors.security.RolesDetailTab;
import it.ldsoftware.primavera.vaadin.layouts.AbstractEditor;
import it.ldsoftware.primavera.vaadin.layouts.AbstractPersonForm;
import it.ldsoftware.primavera.vaadin.util.GroupAdder;
import it.ldsoftware.primavera.vaadin.util.RoleAdder;
import org.vaadin.viritin.fields.MPasswordField;
import org.vaadin.viritin.fields.MTextField;

import static com.vaadin.ui.themes.ValoTheme.TEXTFIELD_TINY;
import static it.ldsoftware.primavera.presentation.people.UserDTO.*;
import static it.ldsoftware.primavera.i18n.CommonLabels.TAB_GROUPS;
import static it.ldsoftware.primavera.i18n.CommonLabels.TAB_ROLES;
import static it.ldsoftware.primavera.i18n.LanguageUtils.getCheckboxName;
import static it.ldsoftware.primavera.i18n.LanguageUtils.getTextFieldName;
import static it.ldsoftware.primavera.vaadin.theme.MetricConstants.FIELD_WIDTH;
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

    public UserForm(AbstractEditor parent) {
        super(parent);
    }

    @Override
    public void addPersonalFields(VerticalLayout generalTab) {
        switchFields(PersonType.REAL);

        username = new MTextField(getTranslator().translate(getTextFieldName(FIELD_USERNAME))).withWidth(FIELD_WIDTH).withNullRepresentation("");
        password = new MPasswordField(getTranslator().translate(getTextFieldName(FIELD_PASSWORD))).withWidth(FIELD_WIDTH).withNullRepresentation("");
        confirmPassword = new MPasswordField(getTranslator().translate(getTextFieldName(FIELD_CONFIRM_PASSWORD))).withWidth(FIELD_WIDTH).withNullRepresentation("");
        enabled = new CheckBox(getTranslator().translate(getCheckboxName(FIELD_ENABLED)));
        primaryEmail = new MTextField(getTranslator().translate(getTextFieldName(FIELD_PRIMARY_EMAIL))).withWidth(FIELD_WIDTH).withNullRepresentation("");

        generalTab.addComponentAsFirst(primaryEmail);
        generalTab.addComponentAsFirst(confirmPassword);
        generalTab.addComponentAsFirst(password);
        generalTab.addComponentAsFirst(username);
        generalTab.addComponent(enabled);

        setStyles();
        wireEvents();
    }

    private void setStyles() {
        username.setStyleName(TEXTFIELD_TINY);
        password.setStyleName(TEXTFIELD_TINY);
        confirmPassword.setStyleName(TEXTFIELD_TINY);
        primaryEmail.setStyleName(TEXTFIELD_TINY);
    }

    @Override
    public void addOtherTabs() {
        super.addOtherTabs();
        addRolesTab();
        addGroupsTab();
    }

    @Override
    public void addRoles(RoleCollector... roles) {
        getBean().addRoles(roles);
    }

    @Override
    public void remRoles(RoleCollector... roles) {
        getBean().remRoles(roles);
    }

    @Override
    public void addGroups(Group... groups) {
        getBean().addGroups(groups);
    }

    @Override
    public void remGroups(Group... groups) {
        getBean().remGroups(groups);
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
