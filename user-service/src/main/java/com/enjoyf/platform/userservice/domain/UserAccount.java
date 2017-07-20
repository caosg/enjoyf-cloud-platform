package com.enjoyf.platform.userservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * A UserAccount.
 */
@RedisHash("userAccounts")
@Entity
@Table(name = "user_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max=40)
    @Column(name = "account_no", nullable = false,unique = true,updatable = false)
    @Indexed
    private String accountNo;

    @NotNull
    @Column(name = "created_time", nullable = false)
    private ZonedDateTime createdTime;

    @NotNull
    @Column(name = "flag", nullable = false)
    private Integer flag;

    @Column(name = "address")
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public UserAccount accountNo(String accountNo) {
        this.accountNo = accountNo;
        return this;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public UserAccount createdTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getFlag() {
        return flag;
    }

    public UserAccount flag(Integer flag) {
        this.flag = flag;
        return this;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserAccount userAccount = (UserAccount) o;
        if (userAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserAccount{" +
            "id=" + getId() +
            ", accountNo='" + getAccountNo() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", flag='" + getFlag() + "'" +
            "}";
    }
}
