package com.enjoyf.platform.userservice.service.dto;

import com.enjoyf.platform.userservice.domain.UserAccount;
import com.enjoyf.platform.userservice.domain.UserLogin;
import com.enjoyf.platform.userservice.domain.UserProfile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

/**
 * Created by shuguangcao on 2017/3/18.
 */
@RedisHash("accounts")
public class AccountDTO {



    @Id
    private Long id;

    @Indexed
    private String profileNo;
    private UserLogin userLogin;
    private UserAccount userAccount;
    private UserProfile userProfile;
    @TimeToLive
    private Long tokenValiditySeconds;

    public AccountDTO() {
    }

    public AccountDTO(UserLogin userLogin, UserAccount userAccount, UserProfile userProfile) {
        this.userLogin = userLogin;
        this.userAccount = userAccount;
        this.userProfile = userProfile;
    }

    public AccountDTO(Long id,String proflieNo, UserLogin userLogin, UserAccount userAccount, UserProfile userProfile) {
        this.id = id;
        this.profileNo = proflieNo;
        this.userLogin = userLogin;
        this.userAccount = userAccount;
        this.userProfile = userProfile;
    }

    public UserLogin getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Long getTokenValiditySeconds() {
        return tokenValiditySeconds;
    }

    public void setTokenValiditySeconds(Long tokenValiditySeconds) {
        this.tokenValiditySeconds = tokenValiditySeconds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfileNo() {
        return profileNo;
    }

    public void setProfileNo(String profileNo) {
        this.profileNo = profileNo;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
            ", userLogin=" + userLogin +
            ", userAccount=" + userAccount +
            ", userProfile=" + userProfile +
            ", tokenValiditySeconds=" + tokenValiditySeconds +
            '}';
    }
}
