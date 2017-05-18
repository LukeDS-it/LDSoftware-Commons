package it.ldsoftware.primavera.vaadin.layouts;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;
import it.ldsoftware.primavera.model.base.AppProperty;
import it.ldsoftware.primavera.model.base.QAppProperty;
import it.ldsoftware.primavera.i18n.LocalizationService;
import it.ldsoftware.primavera.services.interfaces.DatabaseService;
import it.ldsoftware.primavera.vaadin.dialogs.TermsOfServiceDialog;
import it.ldsoftware.primavera.vaadin.theme.MetricConstants;
import it.ldsoftware.primavera.vaadin.util.SecurityUtils;
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
import static com.vaadin.server.FontAwesome.*;
import static com.vaadin.ui.Alignment.MIDDLE_CENTER;
import static com.vaadin.ui.themes.ValoTheme.*;
import static it.ldsoftware.primavera.i18n.CommonButtons.*;
import static it.ldsoftware.primavera.i18n.CommonErrors.*;
import static it.ldsoftware.primavera.i18n.CommonLabels.*;
import static it.ldsoftware.primavera.i18n.CommonMessages.MSG_TERM_SERVICE;

/**
 * Created by luca on 20/04/16.
 * This class defines a layout commonly used for the login page of a web application.
 * The background image can be customized using the css
 */
public class LoginLayout extends MVerticalLayout {

    private LocalizationService msg;
    private DatabaseService service;
    private UI ui;
    private VaadinSecurity sec;

    private TextField txtUsername;
    private PasswordField password;
    private Button btnLogin;
    private Label errorLabel, lblLogin;

    private VerticalLayout form;

    private HorizontalLayout additionalLogins;

    private CheckBox cb;

    public LoginLayout(LocalizationService msg, UI ui, VaadinSecurity sec, DatabaseService service) {
        this.msg = msg;
        this.ui = ui;
        this.sec = sec;
        this.service = service;

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
        checkButtonLayoutExists();
        Button btnGoogle = new MButton(msg.translate(BTN_GOOGLE_LOGIN))
                .withStyleName(BUTTON_DANGER)
                .withListener(this::googleLogin)
                .withIcon(GOOGLE);
        additionalLogins.addComponent(btnGoogle);
        return this;
    }

    public LoginLayout withFacebookSignIn() {
        checkButtonLayoutExists();
        Button btnFacebook = new MButton(msg.translate(BTN_FACEBOOK_LOGIN))
                .withStyleName(BUTTON_PRIMARY)
                .withListener(this::facebookLogin)
                .withIcon(FACEBOOK);
        additionalLogins.addComponent(btnFacebook);
        return this;
    }

    public LoginLayout withTwitterSignIn() {
        checkButtonLayoutExists();
        Button btnTwitter = new MButton(msg.translate(BTN_TWITTER_LOGIN))
                .withStyleName(BUTTON_PRIMARY)
                .withListener(this::twitterLogin)
                .withIcon(TWITTER);
        additionalLogins.addComponent(btnTwitter);
        return this;
    }

    private void checkButtonLayoutExists() {
        if (additionalLogins == null) {
            additionalLogins = new MHorizontalLayout();
            addToForm(additionalLogins);
        }
    }

    private void googleLogin(ClickEvent event) {
        String callback = Page.getCurrent().getLocation() + "googleCallback";
        String gK;
        String gS;

        try {
            QAppProperty p = QAppProperty.appProperty;
            gK = service.findOne(AppProperty.class, p.key.eq("google.client.id")).getStringVal();
            gS = service.findOne(AppProperty.class, p.key.eq("google.client.secret")).getStringVal();

            String redirect = SecurityUtils.getGoogleService(callback, gK, gS).getAuthorizationUrl(null);
            ui.getPage().open(redirect, "_self");
        } catch (NullPointerException e) {
            errorLabel.setValue(msg.translate(ERROR_GOOGLE_AUTH_CONFIG));
        }
    }

    private void facebookLogin(ClickEvent event) {
        // TODO
    }

    private void twitterLogin(ClickEvent event) {
        // TODO
    }

    private void openTermsOfService(ClickEvent event) {
        new TermsOfServiceDialog(msg).popup(TITLE_TOS);
    }

    private void checkBox(ValueChangeEvent event) {
        btnLogin.setEnabled(cb.getValue());
    }
}
