package com.enjoyf.platform.contentservice.web.rest.vm;

import javax.validation.constraints.NotNull;

/**
 * Created by zhimingli on 2017/5/24.
 */
public class ContentWikiVM {

    @NotNull
    private String wikikey;

    @NotNull
    private String title;

    @NotNull
    private String wikiname;

    @NotNull
    private String weburl;

    @NotNull
    private Long publishtime;


    public String getWikikey() {
        return wikikey;
    }

    public void setWikikey(String wikikey) {
        this.wikikey = wikikey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWikiname() {
        return wikiname;
    }

    public void setWikiname(String wikiname) {
        this.wikiname = wikiname;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public Long getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Long publishtime) {
        this.publishtime = publishtime;
    }
}


