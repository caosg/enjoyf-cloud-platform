package com.enjoyf.platform.userservice.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.enjoyf.platform.userservice.domain.enumeration.AppType;

import com.enjoyf.platform.userservice.domain.enumeration.ValidStatus;

import com.enjoyf.platform.userservice.domain.enumeration.ProfileKey;
import org.springframework.data.annotation.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

/**
 * A AppConf.
 */
@Entity
@Table(name = "app_conf")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@RedisHash("appConf")
public class AppConf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 40)
    @Column(name = "app_key", length = 40, nullable = false)
    @Indexed
    private String appKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "app_type")
    private AppType appType;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ValidStatus status;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "profile_key", nullable = false)
    private ProfileKey profileKey;

    @NotNull
    @Size(max = 64)
    @Column(name = "app_name", length = 64, nullable = false)
    private String appName;

    @Column(name = "config")
    private String config = "{}";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppKey() {
        return appKey;
    }

    public AppConf appKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public AppType getAppType() {
        return appType;
    }

    public AppConf appType(AppType appType) {
        this.appType = appType;
        return this;
    }

    public void setAppType(AppType appType) {
        this.appType = appType;
    }

    public String getDescription() {
        return description;
    }

    public AppConf description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ValidStatus getStatus() {
        return status;
    }

    public AppConf status(ValidStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ValidStatus status) {
        this.status = status;
    }

    public ProfileKey getProfileKey() {
        return profileKey;
    }

    public AppConf profileKey(ProfileKey profileKey) {
        this.profileKey = profileKey;
        return this;
    }

    public void setProfileKey(ProfileKey profileKey) {
        this.profileKey = profileKey;
    }

    public String getAppName() {
        return appName;
    }

    public AppConf appName(String appName) {
        this.appName = appName;
        return this;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getConfig() {
        return config;
    }

    public AppConf config(String config) {
        this.config = config;
        return this;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AppConf appConf = (AppConf) o;
        if (appConf.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appConf.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppConf{" +
            "id=" + getId() +
            ", appKey='" + getAppKey() + "'" +
            ", appType='" + getAppType() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", profileKey='" + getProfileKey() + "'" +
            ", appName='" + getAppName() + "'" +
            ", config='" + getConfig() + "'" +
            "}";
    }
}
