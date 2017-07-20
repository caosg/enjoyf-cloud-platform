package com.enjoyf.platform.messageservice.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PushProfileDeviceDTO.
 */
public class PushProfileDeviceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String appkey;

    private String deviceid;

    private Long uid;

    private Integer platform;

    private String tags;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getAppkey() + getDeviceid() + getUid() + getPlatform());
    }

    @Override
    public String toString() {
        return "PushProfileDeviceDTO{" +
            ", appkey='" + getAppkey() + "'" +
            ", deviceId='" + getDeviceid() + "'" +
            ", profileNo='" + getUid() + "'" +
            ", Platform='" + getPlatform() + "'" +
            "}";
    }
}
