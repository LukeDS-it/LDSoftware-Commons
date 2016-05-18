package it.ldsoftware.primavera.i18n.configuration;

import it.ldsoftware.primavera.i18n.WildcardMessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Created by luca on 18/05/16.
 * This class contains the configuration that allows internationalization of
 * an application.
 *
 * To correctly translate your application, you must insert the message
 * bundles inside the path <code>${resourcePath}/it/ldsoftware/yourAppName/i18n</code>
 *
 * Recognised bundles are the following, and they should contain:
 * <ul>
 *     <li><code>messages</code> for messages shown on screen such as popups</li>
 *     <li><code>buttons</code> for labels that are applied to buttons</li>
 *     <li><code>labels</code> for field labels</li>
 *     <li><code>columns</code> for column headers</li>
 *     <li><code>errors</code> for error messages and warnings</li>
 * </ul>
 */
@Configuration
public class LanguageConfiguration {

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new WildcardMessageSource();

        messageSource.setBasenames(
                "classpath*:it/ldsoftware/*/i18n/messages*.properties",
                "classpath*:it/ldsoftware/*/i18n/buttons*.properties",
                "classpath*:it/ldsoftware/*/i18n/labels*.properties",
                "classpath*:it/ldsoftware/*/i18n/columns*.properties",
                "classpath*:it/ldsoftware/*/i18n/errors*.properties",
                "classpath*:it/ldsoftware/*/i18n/nations*.properties");
        messageSource.setCacheSeconds(3600);
        messageSource.setFallbackToSystemLocale(false);

        return messageSource;
    }

}
