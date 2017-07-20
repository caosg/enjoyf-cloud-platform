package com.enjoyf.platform.userservice.domain.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ProfileRating.
 */
public class ProfileRating implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String RATING_MOBILE="mobile";

    private String profileNo;

    private String type;

    private Integer mobileTimes = 0;

    public String getProfileNo() {
        return profileNo;
    }

    public ProfileRating profileNo(String profileNo) {
        this.profileNo = profileNo;
        return this;
    }

    public void setProfileNo(String profileNo) {
        this.profileNo = profileNo;
    }

    public Integer getMobileTimes() {
        return mobileTimes;
    }

    public ProfileRating times(Integer times) {
        this.mobileTimes = times;
        return this;
    }

    public void setMobileTimes(Integer mobileTimes) {
        this.mobileTimes = mobileTimes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProfileRating profileRating = (ProfileRating) o;
        if (profileRating.profileNo == null || profileNo == null) {
            return false;
        }
        return Objects.equals(profileNo, profileRating.profileNo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(profileNo);
    }

    @Override
    public String toString() {
        return "ProfileRating{" +
            ", profileNo='" + getProfileNo() + "'" +
            ", times='" + getMobileTimes() + "'" +
            "}";
    }
}
