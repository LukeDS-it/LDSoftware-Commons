package it.ldsoftware.rest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration for the REST service
 */
@ConfigurationProperties(prefix = "it.primavera.rest")
public class RestProperties {

    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
