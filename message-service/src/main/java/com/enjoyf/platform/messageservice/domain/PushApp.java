package com.enjoyf.platform.messageservice.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PushApp.
 */
@Entity
@Table(name = "push_app")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@RedisHash("pushApp")
public class PushApp {

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max=36)
    @Column(name = "appkey", nullable = false,unique = true,updatable = false)
    @Indexed
    private String appkey;

    @NotNull
    @Size(max=36)
    @Column(name = "third_appkey", nullable = false)
    private String thirdAppkey;

    @NotNull
    @Size(max=36)
    @Column(name = "third_secrkey")
    private String thirdSecrkey;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppkey() {
        return appkey;
    }

    public PushApp appkey(String appkey) {
        this.appkey = appkey;
        return this;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getThirdAppkey() {
        return thirdAppkey;
    }

    public PushApp thirdAppkey(String thirdAppkey) {
        this.thirdAppkey = thirdAppkey;
        return this;
    }

    public void setThirdAppkey(String thirdAppkey) {
        this.thirdAppkey = thirdAppkey;
    }

    public String getThirdSecrkey() {
        return thirdSecrkey;
    }

    public PushApp thirdSecrkey(String thirdSecrkey) {
        this.thirdSecrkey = thirdSecrkey;
        return this;
    }

    public void setThirdSecrkey(String thirdSecrkey) {
        this.thirdSecrkey = thirdSecrkey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PushApp pushApp = (PushApp) o;
        if (pushApp.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pushApp.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PushApp{" +
            "id=" + getId() +
            ", appkey='" + getAppkey() + "'" +
            ", thirdAppkey='" + getThirdAppkey() + "'" +
            ", thirdSecrkey='" + getThirdSecrkey() + "'" +
            "}";
    }
}
