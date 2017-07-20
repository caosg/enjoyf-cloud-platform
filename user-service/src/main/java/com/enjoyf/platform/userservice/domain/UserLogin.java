package com.enjoyf.platform.userservice.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A UserLogin.
 */
@Entity
@Table(name = "user_login")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 4, max = 64)
    @Column(name = "login", length = 64, nullable = false)
    private String login;

    @NotNull
    @Size(min = 6, max = 64)
    @Column(name = "password", length = 64)
    private String password;

    @Size(max = 32)
    @Column(name = "login_name", length = 32)
    private String loginName;

    @NotNull
    @Size(max = 32)
    @Column(name = "login_domain", length = 32, nullable = false)
    private String loginDomain;

    @NotNull
    @Size(max = 40)
    @Column(name = "account_no", length = 40, nullable = false)
    private String accountNo;

    @NotNull
    @Column(name = "created_time", nullable = false)
    private ZonedDateTime createdTime;

    @Column(name = "activated")
    private Boolean activated=true;

    @Size(max = 24)
    @Column(name = "created_ip", length = 24)
    private String createdIp;

    @Column(name = "updated_time")
    private ZonedDateTime updatedTime;

    @Size(max = 32)
    @Column(name = "password_time", length = 32)
    private String passwordTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public UserLogin login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public UserLogin password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginName() {
        return loginName;
    }

    public UserLogin loginName(String loginName) {
        this.loginName = loginName;
        return this;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginDomain() {
        return loginDomain;
    }

    public UserLogin loginDomain(String loginDomain) {
        this.loginDomain = loginDomain;
        return this;
    }

    public void setLoginDomain(String loginDomain) {
        this.loginDomain = loginDomain;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public UserLogin accountNo(String accountNo) {
        this.accountNo = accountNo;
        return this;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public UserLogin createdTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Boolean isActivated() {
        return activated;
    }

    public UserLogin activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getCreatedIp() {
        return createdIp;
    }

    public UserLogin createdIp(String createdIp) {
        this.createdIp = createdIp;
        return this;
    }

    public void setCreatedIp(String createdIp) {
        this.createdIp = createdIp;
    }

    public ZonedDateTime getUpdatedTime() {
        return updatedTime;
    }

    public UserLogin updatedTime(ZonedDateTime updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    public void setUpdatedTime(ZonedDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getPasswordTime() {
        return passwordTime;
    }

    public UserLogin passwordTime(String passwordTime) {
        this.passwordTime = passwordTime;
        return this;
    }

    public void setPasswordTime(String passwordTime) {
        this.passwordTime = passwordTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserLogin userLogin = (UserLogin) o;
        if (userLogin.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userLogin.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserLogin{" +
            "id=" + getId() +
            ", login='" + getLogin() + "'" +
            ", password='" + getPassword() + "'" +
            ", loginName='" + getLoginName() + "'" +
            ", loginDomain='" + getLoginDomain() + "'" +
            ", accountNo='" + getAccountNo() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", activated='" + isActivated() + "'" +
            ", createdIp='" + getCreatedIp() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", passwordTime='" + getPasswordTime() + "'" +
            "}";
    }
}
