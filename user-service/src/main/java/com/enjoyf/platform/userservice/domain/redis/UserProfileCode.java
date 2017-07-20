package com.enjoyf.platform.userservice.domain.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;


/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:2017/3/24
 */
@RedisHash("userProfileCodes")
public class UserProfileCode {

    @Id
    String profileNo;

    String mobile;
    String mobileCode;

    @TimeToLive
    private Long expiration;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProfileNo() {
        return profileNo;
    }

    public void setProfileNo(String profileNo) {
        this.profileNo = profileNo;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    @Override
    public String toString() {
        return "UserProfileCode{" +
            "profileNo='" + profileNo + '\'' +
            ", mobileCode='" + mobileCode + '\'' +
            ", expiration=" + expiration +
            '}';
    }
}
