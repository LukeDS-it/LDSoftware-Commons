package it.ldsoftware.commons.vaadin.layouts;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;
import it.ldsoftware.commons.i18n.LocalizationService;
import it.ldsoftware.commons.vaadin.dialogs.TermsOfServiceDialog;
import it.ldsoftware.commons.vaadin.theme.MetricConstants;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.vaadin.spring.security.VaadinSecurity;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import java.util.Locale;

import static com.vaadin.event.ShortcutAction.KeyCode.ENTER;
import static com.vaadin.server.FontAwesome.KEY;
import static com.vaadin.ui.Alignment.MIDDLE_CENTER;
import static com.vaadin.ui.themes.ValoTheme.*;
import static it.ldsoftware.commons.i18n.CommonLabels.*;

/**
 * Created by luca on 20/04/16.
 * This class defines a layout commonly used for the login page of a web application.
 * The background image can be customized using the css
 */
public class LoginLayout extends MVerticalLayout {

    private LocalizationService msg;
    private UI ui;
    private VaadinSecurity sec;

    private TextField txtUsername;
    private PasswordField password;
    private Button btnLogin;
    private Label errorLabel, lblLogin;

    private VerticalLayout form;

    private CheckBox cb;

    public LoginLayout(LocalizationService msg, UI ui, VaadinSecurity sec) {
        this.msg = msg;
        this.ui = ui;
        this.sec = sec;

        initComponents();

        HorizontalLayout middle;

        form = new MVerticalLayout(new MHorizontalLayout(lblLogin).withFullWidth(), errorLabel,
                (middle = new MHorizontalLayout(txtUsername, password, btnLogin).withFullWidth().withAlign(btnLogin,
                        Alignment.BOTTOM_LEFT))).withWidth("66%").withStyleName("login");

        middle.setExpandRatio(txtUsername, 2);
        middle.setExpandRatio(password, 2);
        middle.setExpandRatio(btnLogin, 1);

        setSizeFull();
        addComponent(form);
        setComponentAlignment(form, MIDDLE_CENTER);
        setStyleName("login-layout");
        txtUsername.focus();
    }

    public void addToForm(Component component) {
        form.addComponent(component);
    }

    private void initComponents() {
        Locale locale = ui.getLocale();
        lblLogin = new Label(msg.translate(LABEL_LOGIN));
        lblLogin.addStyleName(ValoTheme.LABEL_H1);

        errorLabel = new Label();
        errorLabel.addStyleName(LABEL_FAILURE);
        errorLabel.addStyleName(LABEL_H3);
        errorLabel.setVisible(false);

        txtUsername = new MTextField(msg.translate(USERNAME));
        txtUsername.setIcon(FontAwesome.USER);
        txtUsername.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        txtUsername.setWidth(MetricConstants.FULL);

        password = new PasswordField(msg.translate(PASSWORD));
        password.setIcon(FontAwesome.LOCK);
        password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        password.setWidth(MetricConstants.FULL);

        btnLogin = new Button(msg.translate(BTN_LOGIN), KEY);
        btnLogin.addStyleName(ValoTheme.BUTTON_DANGER);
        btnLogin.setClickShortcut(ENTER);
        btnLogin.addClickListener(this::clickSignIn);
    }

    private void clickSignIn(Event e) {
        try {
            sec.login(txtUsername.getValue(), password.getValue());
        } catch (CredentialsExpiredException cee) {
            errorLabel.setValue(msg.translate(ERROR_CREDENTIALS_EXPIRED));
            errorLabel.setVisible(true);
        } catch (BadCredentialsException bce) {
            errorLabel.setValue(msg.translate(ERROR_BAD_CREDENTIALS));
            errorLabel.setVisible(true);
        } catch (DisabledException de) {
            errorLabel.setValue(msg.translate(ERROR_DISABLED));
            errorLabel.setVisible(true);
        } catch (AuthenticationException e1) {
            e1.printStackTrace();
            errorLabel.setValue(msg.translate(ERROR_LOGIN_ACCOUNT));
            errorLabel.setVisible(true);
        } catch (Exception e1) {
            e1.printStackTrace();
            errorLabel.setValue(msg.translate(ERROR_LOGIN_GENERIC));
            errorLabel.setVisible(true);
        }
    }

    public LoginLayout withTOS() {
        cb = new CheckBox();
        cb.addValueChangeListener(this::checkBox);
        Button openTermsOfService = new MButton(msg.translate(MSG_TERM_SERVICE))
                .withStyleName(BUTTON_LINK)
                .withListener(this::openTermsOfService);

        addToForm(new MHorizontalLayout(cb, openTermsOfService).withAlign(cb, MIDDLE_CENTER));
        btnLogin.setEnabled(false);

        return this;
    }

    public LoginLayout withGoogleSignIn() {
        // TODO
        return this;
    }

    public LoginLayout withFacebookSignIn() {
        // TODO
        return this;
    }

    public LoginLayout withTwitterSignIn() {
        // TODO
        return this;
    }

    private void openTermsOfService(ClickEvent event) {
        new TermsOfServiceDialog(msg).popup(TITLE_TOS);
    }

    private void checkBox(ValueChangeEvent event) {
        btnLogin.setEnabled(cb.getValue());
    }
}
