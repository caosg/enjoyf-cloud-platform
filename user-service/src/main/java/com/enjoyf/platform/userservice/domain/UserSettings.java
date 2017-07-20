package com.enjoyf.platform.userservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.*;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A UserSettings.
 */
@Entity
@Table(name = "user_settings")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@RedisHash("user-settings")
public class UserSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.annotation.Id
    private Long id;

    @NotNull
    @Size(max = 40)
    @Column(name = "profile_no", nullable = false,unique = true)
    @Indexed
    private String profileNo;

    @Column(name = "alarm_settings")
    private String alarmSettings;

    @Column(name = "func_settings")
    private String funcSettings;

    @Column(name = "created_time", nullable = false)
    @JsonIgnore
    private ZonedDateTime createdTime;

    @Column(name = "updated_time")
    @JsonIgnore
    private ZonedDateTime updatedTime;

    @Column(name = "created_ip")
    private String createdIp;

    @Column(name = "updated_ip")
    private String updatedIp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfileNo() {
        return profileNo;
    }

    public UserSettings profileNo(String profileNo) {
        this.profileNo = profileNo;
        return this;
    }

    public void setProfileNo(String profileNo) {
        this.profileNo = profileNo;
    }

    public String getAlarmSettings() {
        return alarmSettings;
    }

    public UserSettings alarmSettings(String alarmSettings) {
        this.alarmSettings = alarmSettings;
        return this;
    }

    public void setAlarmSettings(String alarmSettings) {
        this.alarmSettings = alarmSettings;
    }

    public String getFuncSettings() {
        return funcSettings;
    }

    public UserSettings funcSettings(String funcSettings) {
        this.funcSettings = funcSettings;
        return this;
    }

    public void setFuncSettings(String funcSettings) {
        this.funcSettings = funcSettings;
    }

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public UserSettings createdTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public ZonedDateTime getUpdatedTime() {
        return updatedTime;
    }

    public UserSettings updatedTime(ZonedDateTime updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    public void setUpdatedTime(ZonedDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getCreatedIp() {
        return createdIp;
    }

    public UserSettings createdIp(String createdIp) {
        this.createdIp = createdIp;
        return this;
    }

    public void setCreatedIp(String createdIp) {
        this.createdIp = createdIp;
    }

    public String getUpdatedIp() {
        return updatedIp;
    }

    public UserSettings updatedIp(String updatedIp) {
        this.updatedIp = updatedIp;
        return this;
    }

    public void setUpdatedIp(String updatedIp) {
        this.updatedIp = updatedIp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserSettings userSettings = (UserSettings) o;
        if (userSettings.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userSettings.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserSettings{" +
            "id=" + getId() +
            ", profileNo='" + getProfileNo() + "'" +
            ", alarmSettings='" + getAlarmSettings() + "'" +
            ", funcSettings='" + getFuncSettings() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", createdIp='" + getCreatedIp() + "'" +
            ", updatedIp='" + getUpdatedIp() + "'" +
            "}";
    }
}
