package com.enjoyf.platform.messageservice.domain;

import com.enjoyf.platform.common.domain.enumeration.ValidStatus;
import com.enjoyf.platform.messageservice.domain.enumration.AppMessageType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.*;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A AppUserMessage.
 */
@Entity
@Table(name = "app_usermessage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@RedisHash("app_usermessage")
public class AppUserMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "appkey", nullable = false)
    private String appkey;

    @NotNull
    @Column(name = "message_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private AppMessageType messageType;

    @NotNull
    @Column(name = "uid", nullable = false)
    private Long uid;

    @Column(name = "message_body")
    private String messageBody;//message body to String

    @Column(name = "read_status")
    @Enumerated(EnumType.ORDINAL)
    private ValidStatus readStatus = ValidStatus.UNVALID; //0-未读

    @Column(name = "valid_status")
    @Enumerated(EnumType.ORDINAL)
    private ValidStatus validStatus = ValidStatus.VALID; //1-可用 0-不可用

    @Column(name = "create_time")
    private ZonedDateTime createTime=ZonedDateTime.now();

    @Column(name = "jt")
    private int jt;

    @Column(name = "ji")
    private String ji;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppkey() {
        return appkey;
    }

    public AppUserMessage appkey(String appkey) {
        this.appkey = appkey;
        return this;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public AppMessageType getMessageType() {
        return messageType;
    }

    public AppUserMessage messageType(AppMessageType messageType) {
        this.messageType = messageType;
        return this;
    }

    public void setMessageType(AppMessageType messageType) {
        this.messageType = messageType;
    }

    public Long getUid() {
        return uid;
    }

    public AppUserMessage uid(Long uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public AppUserMessage messageBody(String messageBody) {
        this.messageBody = messageBody;
        return this;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public int getJt() {
        return jt;
    }

    public void setJt(int jt) {
        this.jt = jt;
    }

    public String getJi() {
        return ji;
    }

    public void setJi(String ji) {
        this.ji = ji;
    }

    public ValidStatus getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(ValidStatus readStatus) {
        this.readStatus = readStatus;
    }

    public ValidStatus getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(ValidStatus validStatus) {
        this.validStatus = validStatus;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AppUserMessage appUserMessage = (AppUserMessage) o;
        if (appUserMessage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appUserMessage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppUserMessage{" +
            "id=" + getId() +
            ", appkey='" + getAppkey() + "'" +
            ", messageType='" + getMessageType() + "'" +
            ", uid='" + getUid() + "'" +
            ", messageBody='" + getMessageBody() + "'" +
            "}";
    }
}
