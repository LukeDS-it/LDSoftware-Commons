package it.ldsoftware.primavera.vaadin.controllers;

import com.google.gson.Gson;
import it.ldsoftware.primavera.entities.base.AppProperty;
import it.ldsoftware.primavera.entities.base.QAppProperty;
import it.ldsoftware.primavera.services.interfaces.DatabaseService;
import it.ldsoftware.primavera.vaadin.exceptions.GAuthConfigurationException;
import it.ldsoftware.primavera.vaadin.security.GoogleResponse;
import it.ldsoftware.primavera.vaadin.security.LoginProvider;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.vaadin.spring.security.VaadinSecurity;

import javax.servlet.http.HttpServletRequest;

import static it.ldsoftware.primavera.vaadin.util.SecurityUtils.getGoogleService;

/**
 * Created by luca on 02/05/16.
 * This controller handles the return of the user from an oauth2 provider.
 * <p>
 * If the application is correctly configured with client id and secret,
 * the user will be logged in, if they are already in the database.
 * If not, a new user will be created with all available details
 * coming in from the provider (e.g. name, surname, email...) and logged in.
 */
@Controller
public class OAuth2CallbackController {

    @Autowired
    private VaadinSecurity security;

    @Autowired
    private LoginProvider provider;

    @Autowired
    private DatabaseService service;

    @RequestMapping("/googleCallback")
    public void loginFromGoogle(HttpServletRequest request, @RequestParam String code) throws Exception {
        Verifier v = new Verifier(code);

        String gApiKey;
        String gApiSecret;

        try {
            QAppProperty p = QAppProperty.appProperty;
            gApiKey = service.findOne(AppProperty.class, p.key.eq("google.client.id")).getStringVal();
            gApiSecret = service.findOne(AppProperty.class, p.key.eq("google.client.secret")).getStringVal();
        } catch (NullPointerException e) {
            throw new GAuthConfigurationException();
        }
        String callback = request.getRequestURL().toString();

        OAuthService svc = getGoogleService(callback, gApiKey, gApiSecret);

        Token t = svc.getAccessToken(null, v);

        OAuthRequest r = new OAuthRequest(Verb.GET, "https://www.googleapis.com/plus/v1/people/me");
        svc.signRequest(t, r);

        Response resp = r.send();

        GoogleResponse googleResponse = new Gson().fromJson(resp.getBody(), GoogleResponse.class);
        security.login(provider.getAuthentication(googleResponse));
    }

}
