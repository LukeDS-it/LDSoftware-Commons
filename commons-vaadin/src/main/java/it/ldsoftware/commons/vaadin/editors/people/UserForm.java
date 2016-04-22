package it.ldsoftware.commons.vaadin.editors.people;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import it.ldsoftware.commons.entities.people.User;
import it.ldsoftware.commons.i18n.CommonLabels;
import it.ldsoftware.commons.util.PersonType;
import it.ldsoftware.commons.util.RoleCollector;
import it.ldsoftware.commons.vaadin.editors.security.RolesTab;
import it.ldsoftware.commons.vaadin.layouts.AbstractPersonForm;
import it.ldsoftware.commons.vaadin.util.RoleAdder;
import org.vaadin.viritin.fields.MPasswordField;
import org.vaadin.viritin.fields.MTextField;

import static it.ldsoftware.commons.i18n.CommonLabels.getCheckboxName;
import static it.ldsoftware.commons.i18n.CommonLabels.getTextFieldName;
import static it.ldsoftware.commons.vaadin.theme.MetricConstants.FIELD_WIDTH;

/**
 * Created by luca on 22/04/16.
 */
public class UserForm extends AbstractPersonForm<User> implements RoleAdder {

    private RolesTab rolesTab;

    private TextField username;
    private PasswordField password, confirmPassword;
    private CheckBox enabled;

    @Override
    public void addPersonalFields(VerticalLayout generalTab) {
        switchFields(PersonType.REAL);

        username = new MTextField(getTranslator().translate(getTextFieldName("username"))).withWidth(FIELD_WIDTH).withNullRepresentation("");
        password = new MPasswordField(getTranslator().translate(getTextFieldName("password"))).withWidth(FIELD_WIDTH).withNullRepresentation("");
        confirmPassword = new MPasswordField(getTranslator().translate(getTextFieldName("confirmPassword"))).withWidth(FIELD_WIDTH).withNullRepresentation("");
        enabled = new CheckBox(getTranslator().translate(getCheckboxName("enabled")));

        generalTab.addComponentAsFirst(confirmPassword);
        generalTab.addComponentAsFirst(password);
        generalTab.addComponentAsFirst(username);
        generalTab.addComponent(enabled);
    }

    @Override
    public void addOtherTabs() {
        super.addOtherTabs();
        addRolesTab();
        addGroupsTab();
    }

    @Override
    public void addRoles(RoleCollector... roles) {

    }

    @Override
    public void remRoles(RoleCollector... roles) {

    }

    @Override
    public User findPerson(long id) {
        return getDatabaseService().findOne(User.class, id);
    }

    private void addRolesTab() {

    }

    private void addGroupsTab() {

    }
}
