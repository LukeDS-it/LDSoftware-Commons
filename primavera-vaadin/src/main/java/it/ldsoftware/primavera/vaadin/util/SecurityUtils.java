package it.ldsoftware.primavera.vaadin.util;

import it.ldsoftware.primavera.vaadin.security.Google2Api;
import org.scribe.builder.ServiceBuilder;
import org.scribe.oauth.OAuthService;

/**
 * Created by luca on 02/05/16.
 * Various useful functions for security.
 */
public class SecurityUtils {

    public static OAuthService getGoogleService(String callback, String gApiKey, String gApiSecret) {
        return new ServiceBuilder()
                .provider(Google2Api.class)
                .scope("openid profile email")
                .apiKey(gApiKey)
                .apiSecret(gApiSecret)
                .callback(callback)
                .build();
    }

}
