package com.enjoyf.platform.contentservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A Content.
 */
@Entity
@Table(name = "content")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Content implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_id")
    private String commentId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    @JsonProperty(value = "describe")
    private String description;

    @Column(name = "pic")
    private String pic;

    @Column(name = "author")
    private String author;

    @Column(name = "game_id")
    @JsonProperty(value = "gameid")
    private String gameId;

    @Column(name = "publish_time")
    private Date publishTime;


    @Column(name = "web_url")
    @JsonProperty(value = "weburl")
    private String webUrl;

    @Column(name = "source")
    private Integer source;

    @Column(name = "remove_status")
    private String removeStatus;

    @Column(name = "create_date")
    private Date createDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentId() {
        return commentId;
    }

    public Content commentId(String commentId) {
        this.commentId = commentId;
        return this;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getTitle() {
        return title;
    }

    public Content title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Content description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic() {
        return pic;
    }

    public Content pic(String pic) {
        this.pic = pic;
        return this;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getAuthor() {
        return author;
    }

    public Content author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGameId() {
        return gameId;
    }

    public Content gameId(String gameId) {
        this.gameId = gameId;
        return this;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }


    public Date getPublishTime() {
        return publishTime;
    }

    public Content publishTime(Date publishTime) {
        this.publishTime = publishTime;
        return this;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public Content webUrl(String webUrl) {
        this.webUrl = webUrl;
        return this;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public Integer getSource() {
        return source;
    }

    public Content source(Integer source) {
        this.source = source;
        return this;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getRemoveStatus() {
        return removeStatus;
    }

    public Content removeStatus(String removeStatus) {
        this.removeStatus = removeStatus;
        return this;
    }

    public void setRemoveStatus(String removeStatus) {
        this.removeStatus = removeStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Content createDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Content content = (Content) o;
        if (content.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), content.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Content{" +
            "id=" + getId() +
            ", commentId='" + getCommentId() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", pic='" + getPic() + "'" +
            ", author='" + getAuthor() + "'" +
            ", gameId='" + getGameId() + "'" +
            ", publishTime='" + getPublishTime() + "'" +
            ", webUrl='" + getWebUrl() + "'" +
            ", source='" + getSource() + "'" +
            ", removeStatus='" + getRemoveStatus() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            "}";
    }

}
