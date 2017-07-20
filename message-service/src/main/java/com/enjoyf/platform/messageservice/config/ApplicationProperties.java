package com.enjoyf.platform.messageservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to JHipster.
 * <p>
 * <p>
 * Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private String wikiappkey;

    public String getWikiappkey() {
        return wikiappkey;
    }

    public void setWikiappkey(String wikiappkey) {
        this.wikiappkey = wikiappkey;
    }
}
