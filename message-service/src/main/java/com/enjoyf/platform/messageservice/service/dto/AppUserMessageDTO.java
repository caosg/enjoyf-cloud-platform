package com.enjoyf.platform.messageservice.service.dto;

import com.enjoyf.platform.common.domain.enumeration.ValidStatus;
import com.enjoyf.platform.json.ZoneDateTime2TimestampSerializer;
import com.enjoyf.platform.messageservice.domain.enumration.AppMessageType;
import com.enjoyf.platform.messageservice.service.userservice.domain.UserProfileDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.ZonedDateTime;

/**
 * Created by ericliu on 2017/6/19.
 */
public class AppUserMessageDTO {
    private Long id;
    @JsonIgnore
    private String appkey;
    @JsonIgnore
    private long uid;
    @JsonIgnore
    private MessageBody messageBodyObj;
    @JsonIgnore
    private AppMessageType messageType;
    private String bodyText;

    private ValidStatus readStatus;
    private String ji;
    private int jt;

    private UserProfileDTO destUser;

    @JsonSerialize(using = ZoneDateTime2TimestampSerializer.class)
    private ZonedDateTime createTime;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public MessageBody getMessageBodyObj() {
        return messageBodyObj;
    }

    public void setMessageBodyObj(MessageBody messageBodyObj) {
        this.messageBodyObj = messageBodyObj;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public AppMessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(AppMessageType messageType) {
        this.messageType = messageType;
    }

    public String getJi() {
        return ji;
    }

    public void setJi(String ji) {
        this.ji = ji;
    }

    public int getJt() {
        return jt;
    }

    public void setJt(int jt) {
        this.jt = jt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ValidStatus getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(ValidStatus readStatus) {
        this.readStatus = readStatus;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public UserProfileDTO getDestUser() {
        return destUser;
    }

    public void setDestUser(UserProfileDTO destUser) {
        this.destUser = destUser;
    }


    @Override
    public String toString() {
        return "AppUserMessageDTO{" +
            "id=" + id +
            ", appkey='" + appkey + '\'' +
            ", uid=" + uid +
            ", messageBodyObj=" + messageBodyObj +
            ", messageType=" + messageType +
            ", bodyText='" + bodyText + '\'' +
            ", readStatus=" + readStatus +
            ", ji='" + ji + '\'' +
            ", jt=" + jt +
            ", destUser=" + destUser +
            ", createTime=" + createTime +
            '}';
    }
}
