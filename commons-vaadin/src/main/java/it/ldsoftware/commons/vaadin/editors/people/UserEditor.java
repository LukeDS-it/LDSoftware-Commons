package it.ldsoftware.commons.vaadin.editors.people;

import com.vaadin.ui.Button.ClickEvent;
import it.ldsoftware.commons.dto.people.UserDTO;
import it.ldsoftware.commons.entities.people.User;
import it.ldsoftware.commons.vaadin.layouts.AbstractEditor;
import it.ldsoftware.commons.vaadin.layouts.AbstractEditorForm;
import it.ldsoftware.commons.validation.groups.NewUserValidationGroup;
import it.ldsoftware.commons.validation.groups.UserValidationGroup;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static it.ldsoftware.commons.util.UserUtil.ROLE_USER_ADMIN;

/**
 * Created by luca on 22/04/16.
 * Editor view for users
 */
public class UserEditor extends AbstractEditor<User, UserDTO> {

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public Class<UserDTO> getDTOClass() {
        return UserDTO.class;
    }

    @Override
    public AbstractEditorForm<User> getEditorInstance() {
        return new UserForm();
    }

    @Override
    public User createNewObject() {
        return new User();
    }

    @Override
    public Class<?>[] getValidationGroups() {
        if (form().getBean() == null || form().getBean().getId() == 0)
            return new Class<?>[]{UserValidationGroup.class, NewUserValidationGroup.class};
        return new Class<?>[]{UserValidationGroup.class};
    }

    private String oldPW;
    private String oldConf;

    @Override
    public void preSaveAction() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (((UserForm) form()).isChangedPassword()) {
            oldPW = form().getBean().getPassword();
            oldConf = form().getBean().getConfirmPassword();
            form().getBean().setPassword(encoder.encode(oldPW));
            form().getBean().setConfirmPassword(form().getBean().getPassword());
        } else {
            long id = form().getBean().getId();
            User u = getSvc().findOne(User.class, id);
            form().getBean().setPassword(u.getPassword());
            form().getBean().setConfirmPassword(u.getPassword());
            oldPW = form().getBean().getPassword();
            oldConf = form().getBean().getConfirmPassword();
        }

    }

    @Override
    public void saveAction(ClickEvent event) {
        super.saveAction(event);
        form().getBean().setConfirmPassword(null);
    }

    @Override
    public void customRollback() {
        form().getBean().setPassword(oldPW);
        form().getBean().setConfirmPassword(oldConf);
    }

    @Override
    protected String getBasePermission() {
        return ROLE_USER_ADMIN;
    }
}
