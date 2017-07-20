package com.enjoyf.platform.profileservice.domain;

import com.enjoyf.platform.profileservice.domain.enumeration.VerifyProfileType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.*;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A VerifyProfile.
 */
@Entity
@Table(name = "verify_profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@RedisHash("verifyprofile")
public class VerifyProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.annotation.Id
    private Long id;

    @Column(name = "verify_info")
    private String verifyInfo = "";

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "verify_type", nullable = false)
    private VerifyProfileType verifyType = VerifyProfileType.VERIFY;

    @NotNull
    @Column(name = "create_time", nullable = false)
    private ZonedDateTime createTime = ZonedDateTime.now();

    @Column(name = "qq", nullable = true)
    private String qq = "";

    @Column(name = "micro_msg", nullable = true)
    private String microMsg = "";

    @Column(name = "mobile", nullable = true)
    private String mobile = "";

    @Column(name = "create_ip")
    private String createIp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getVerifyInfo() {
        return verifyInfo;
    }

    public VerifyProfile verifyInfo(String verifyInfo) {
        this.verifyInfo = verifyInfo;
        return this;
    }

    public void setVerifyInfo(String verifyInfo) {
        this.verifyInfo = verifyInfo;
    }

    public VerifyProfileType getVerifyType() {
        return verifyType;
    }

    public VerifyProfile verifyType(VerifyProfileType verifyType) {
        this.verifyType = verifyType;
        return this;
    }

    public void setVerifyType(VerifyProfileType verifyType) {
        this.verifyType = verifyType;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public VerifyProfile createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public String getCreateIp() {
        return createIp;
    }

    public VerifyProfile createIp(String createIp) {
        this.createIp = createIp;
        return this;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMicroMsg() {
        return microMsg;
    }

    public void setMicroMsg(String microMsg) {
        this.microMsg = microMsg;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VerifyProfile verifyProfile = (VerifyProfile) o;
        if (verifyProfile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), verifyProfile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VerifyProfile{" +
            "id=" + getId() +
            ", verifyInfo='" + getVerifyInfo() + "'" +
            ", verifyType='" + getVerifyType() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", createIp='" + getCreateIp() + "'" +
            "}";
    }
}
