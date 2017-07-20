package com.enjoyf.platform.userservice.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A UserMobile.
 */
@Entity
@Table(name = "user_mobile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserMobile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "profile_no", nullable = false)
    private String profileNo;

    @NotNull
    @Column(name = "account_no", nullable = false)
    private String accountNo;

    @NotNull
    @Column(name = "profile_key", nullable = false)
    private String profileKey = "www";

    @NotNull
    @Size(min = 11)
    @Column(name = "mobile", nullable = false)
    private String mobile;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfileNo() {
        return profileNo;
    }

    public UserMobile profileNo(String profileNo) {
        this.profileNo = profileNo;
        return this;
    }

    public void setProfileNo(String profileNo) {
        this.profileNo = profileNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public UserMobile accountNo(String accountNo) {
        this.accountNo = accountNo;
        return this;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getProfileKey() {
        return profileKey;
    }

    public UserMobile profileKey(String profileKey) {
        this.profileKey = profileKey;
        return this;
    }

    public void setProfileKey(String profileKey) {
        this.profileKey = profileKey;
    }

    public String getMobile() {
        return mobile;
    }

    public UserMobile mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public UserMobile createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
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
        UserMobile userMobile = (UserMobile) o;
        if (userMobile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userMobile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserMobile{" +
            "id=" + getId() +
            ", profileNo='" + getProfileNo() + "'" +
            ", accountNo='" + getAccountNo() + "'" +
            ", profileKey='" + getProfileKey() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
