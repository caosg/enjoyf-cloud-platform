package com.enjoyf.platform.contentservice.web.rest.vm;

import com.enjoyf.platform.contentservice.domain.enumeration.ContentSource;
import com.enjoyf.platform.contentservice.domain.enumeration.ValidStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;

/**
 * Created by zhimingli on 2017/5/24.
 */
public class ContentCmsVM {
    private static final long serialVersionUID = 1L;

    @NotNull
    private Long archiveid;

    @NotNull
    private String title;

    @NotNull
    private String describe;

    @NotNull
    private String pic;

    @NotNull
    private String author;

    @NotNull
    private String gameid;

    @NotNull
    private Long publishtime;

    @NotNull
    private String weburl;

    @JsonIgnore
    private Integer source = ContentSource.CMS_ARCHIVE.getCode();

    @JsonIgnore
    private String removeStatus = ValidStatus.VALID.name();

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGameid() {
        return gameid;
    }

    public void setGameid(String gameid) {
        this.gameid = gameid;
    }

    public Long getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Long publishtime) {
        this.publishtime = publishtime;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public Long getArchiveid() {
        return archiveid;
    }

    public void setArchiveid(Long archiveid) {
        this.archiveid = archiveid;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getRemoveStatus() {
        return removeStatus;
    }

    public void setRemoveStatus(String removeStatus) {
        this.removeStatus = removeStatus;
    }
}
