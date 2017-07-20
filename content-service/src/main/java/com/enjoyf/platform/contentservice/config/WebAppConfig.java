package com.enjoyf.platform.contentservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Component;

/**
 * Created by pengxu on 2017/5/26.
 */
@Component
@ConfigurationProperties(prefix = "host_name")
public class WebAppConfig {
    private String domain;
    private String url_api;
    private String url_wikiser;
    private String solr_query;
    private String solr_save;
    private String solr_delete;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUrl_api() {
        return url_api;
    }

    public void setUrl_api(String url_api) {
        this.url_api = url_api;
    }

    public String getUrl_wikiser() {
        return url_wikiser;
    }

    public void setUrl_wikiser(String url_wikiser) {
        this.url_wikiser = url_wikiser;
    }

    public String getSolr_query() {
        return solr_query;
    }

    public void setSolr_query(String solr_query) {
        this.solr_query = solr_query;
    }

    public String getSolr_save() {
        return solr_save;
    }

    public void setSolr_save(String solr_save) {
        this.solr_save = solr_save;
    }

    public String getSolr_delete() {
        return solr_delete;
    }

    public void setSolr_delete(String solr_delete) {
        this.solr_delete = solr_delete;
    }
}
