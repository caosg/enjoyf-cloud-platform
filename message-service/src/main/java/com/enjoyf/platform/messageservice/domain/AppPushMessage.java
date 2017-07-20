package com.enjoyf.platform.messageservice.domain;

import com.enjoyf.platform.common.domain.enumeration.ValidStatus;
import com.enjoyf.platform.event.message.enumration.PushType;
import com.enjoyf.platform.json.Timestamp2ZoneDateTimeDeserializer;
import com.enjoyf.platform.messageservice.domain.enumration.SendType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A AppPushMessage.
 */
@Entity
@Table(name = "app_push_message")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppPushMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Long id;

    @NotNull
    @Size(max = 36)
    @Column(name = "appkey", length = 36, nullable = false)
    private String appkey;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "jt")
    private Integer jt;

    @Column(name = "ji")
    private String ji;

    @Column(name = "create_time")
    @ApiModelProperty(hidden = true)
    private ZonedDateTime createTime = ZonedDateTime.now();

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "send_time")
    @JsonDeserialize(using = Timestamp2ZoneDateTimeDeserializer.class)
    private ZonedDateTime sendTime = ZonedDateTime.now();

    @NotNull
    @Column(name = "send_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @ApiModelProperty("发送类型，延时还是立即")
    private SendType sendType = SendType.now;

    @NotNull
    @Column(name = "send_status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @ApiModelProperty(hidden = true)
    private ValidStatus sendStatus = ValidStatus.INIT;

    @NotNull
    @Column(name = "remove_status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @ApiModelProperty(hidden = true)
    private ValidStatus removeStatus = ValidStatus.UNVALID;


    @NotNull
    @Column(name = "push_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private PushType pushType = PushType.TAG;

    @Column(name = "push_uid")
    private Long pushUid;

    @Column(name = "tags")
    private String tags;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppkey() {
        return appkey;
    }

    public AppPushMessage appkey(String appkey) {
        this.appkey = appkey;
        return this;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getTitle() {
        return title;
    }

    public AppPushMessage title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public AppPushMessage body(String body) {
        this.body = body;
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getJt() {
        return jt;
    }

    public AppPushMessage jt(Integer jt) {
        this.jt = jt;
        return this;
    }

    public void setJt(Integer jt) {
        this.jt = jt;
    }

    public String getJi() {
        return ji;
    }

    public AppPushMessage ji(String ji) {
        this.ji = ji;
        return this;
    }

    public void setJi(String ji) {
        this.ji = ji;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public AppPushMessage createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public AppPushMessage createUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public ZonedDateTime getSendTime() {
        return sendTime;
    }

    public AppPushMessage sendTime(ZonedDateTime sendTime) {
        this.sendTime = sendTime;
        return this;
    }

    public void setSendTime(ZonedDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public ValidStatus getSendStatus() {
        return sendStatus;
    }

    public AppPushMessage sendStatus(ValidStatus sendStatus) {
        this.sendStatus = sendStatus;
        return this;
    }

    public void setSendStatus(ValidStatus sendStatus) {
        this.sendStatus = sendStatus;
    }

    public ValidStatus getRemoveStatus() {
        return removeStatus;
    }

    public AppPushMessage removeStatus(ValidStatus removeStatus) {
        this.removeStatus = removeStatus;
        return this;
    }

    public void setRemoveStatus(ValidStatus removeStatus) {
        this.removeStatus = removeStatus;
    }

    public PushType getPushType() {
        return pushType;
    }

    public void setPushType(PushType pushType) {
        this.pushType = pushType;
    }

    public Long getPushUid() {
        return pushUid;
    }

    public void setPushUid(Long pushUid) {
        this.pushUid = pushUid;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public SendType getSendType() {
        return sendType;
    }

    public void setSendType(SendType sendType) {
        this.sendType = sendType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AppPushMessage appPushMessage = (AppPushMessage) o;
        if (appPushMessage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appPushMessage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppPushMessage{" +
            "id=" + getId() +
            ", appkey='" + getAppkey() + "'" +
            ", title='" + getTitle() + "'" +
            ", body='" + getBody() + "'" +
            ", jt='" + getJt() + "'" +
            ", ji='" + getJi() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", sendTime='" + getSendTime() + "'" +
            ", sendStatus='" + getSendStatus() + "'" +
            ", removeStatus='" + getRemoveStatus() + "'" +
            "}";
    }
}
