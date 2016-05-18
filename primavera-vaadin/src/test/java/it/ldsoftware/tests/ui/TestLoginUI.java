package it.ldsoftware.tests.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import it.ldsoftware.primavera.i18n.LocalizationService;
import it.ldsoftware.primavera.services.interfaces.DatabaseService;
import it.ldsoftware.primavera.vaadin.layouts.LoginLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.vaadin.spring.security.VaadinSecurity;

/**
 * Created by luca on 16/05/16.
 * UI for login
 */
@Theme("ldsmetro")
@SpringUI(path = "/login")
public class TestLoginUI extends UI {

    @Autowired
    MessageSource source;
    @Autowired
    DatabaseService svc;
    @Autowired
    VaadinSecurity sec;

    @Override
    protected void init(VaadinRequest request) {
        setContent(new LoginLayout(new LocalizationService(source, getLocale()), this, sec, svc));
    }

}
