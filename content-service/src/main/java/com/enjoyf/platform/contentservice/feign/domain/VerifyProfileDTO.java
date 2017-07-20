package com.enjoyf.platform.contentservice.feign.domain;


import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the VerifyProfile entity.
 */
public class VerifyProfileDTO implements Serializable {

    private Long id;

    private String nick;
    private String icon;

    private String verifyInfo;

    @NotNull
    private VerifyProfileType verifyType;

    private ZonedDateTime createTime;

    private String qq;

    private String microMsg;

    private String mobile;

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

    public void setVerifyInfo(String verifyInfo) {
        this.verifyInfo = verifyInfo;
    }

    public VerifyProfileType getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(VerifyProfileType verifyType) {
        this.verifyType = verifyType;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

        VerifyProfileDTO verifyProfileDTO = (VerifyProfileDTO) o;
        if (verifyProfileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), verifyProfileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VerifyProfileDTO{" +
            "id=" + getId() +
            ", verifyInfo='" + getVerifyInfo() + "'" +
            ", verifyType='" + getVerifyType() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", createIp='" + getCreateIp() + "'" +
            "}";
    }
}
