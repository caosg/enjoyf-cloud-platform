package com.enjoyf.platform.messageservice.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PushProfileDevice.
 */
@Entity
@Table(name = "push_profile_device")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PushProfileDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "uid", nullable = false)
    private Long uid;

    @NotNull
    @Size(max = 36)
    @Column(name = "appkey", length = 36, nullable = false)
    private String appkey;

    @NotNull
    @Size(max = 127)
    @Column(name = "device_id", length = 127, nullable = false)
    private String deviceid;

    @NotNull
    @Column(name = "platform", nullable = false)
    private Integer platform;

    @Column(name = "tags")
    private String tags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public PushProfileDevice uid(Long uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getAppkey() {
        return appkey;
    }

    public PushProfileDevice appkey(String appkey) {
        this.appkey = appkey;
        return this;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public PushProfileDevice deviceId(String deviceId) {
        this.deviceid = deviceId;
        return this;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public Integer getPlatform() {
        return platform;
    }

    public PushProfileDevice platform(Integer platform) {
        this.platform = platform;
        return this;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getTags() {
        return tags;
    }

    public PushProfileDevice tags(String tags) {
        this.tags = tags;
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PushProfileDevice pushProfileDevice = (PushProfileDevice) o;
        if (pushProfileDevice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pushProfileDevice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PushProfileDevice{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", appkey='" + getAppkey() + "'" +
            ", deviceId='" + getDeviceid() + "'" +
            ", platform='" + getPlatform() + "'" +
            ", tags='" + getTags() + "'" +
            "}";
    }
}
