package com.enjoyf.platform.contentservice.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * A UserCollect.
 */
@Entity
@Table(name = "user_collect")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserCollect implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profile_id")
    private String profileId;

    @Column(name = "collect_type")
    private Integer collectType;

    @Column(name = "appkey")
    private String appkey;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "content_id")
    private Long contentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfileId() {
        return profileId;
    }

    public UserCollect profileId(String profileId) {
        this.profileId = profileId;
        return this;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public Integer getCollectType() {
        return collectType;
    }

    public UserCollect collectType(Integer collectType) {
        this.collectType = collectType;
        return this;
    }

    public void setCollectType(Integer collectType) {
        this.collectType = collectType;
    }

    public String getAppkey() {
        return appkey;
    }

    public UserCollect appkey(String appkey) {
        this.appkey = appkey;
        return this;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public UserCollect createTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Long getContentId() {
        return contentId;
    }

    public UserCollect contentId(Long contentId) {
        this.contentId = contentId;
        return this;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserCollect userCollect = (UserCollect) o;
        if (userCollect.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userCollect.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserCollect{" +
            "id=" + getId() +
            ", profileId='" + getProfileId() + "'" +
            ", collectType='" + getCollectType() + "'" +
            ", appkey='" + getAppkey() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", contentId='" + getContentId() + "'" +
            "}";
    }
}
