package com.enjoyf.platform.userservice.domain.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Created by shuguangcao on 2017/4/10.
 */
@RedisHash("profile-counts")
public class ProfileCount {
    @Id
   private String profileNo;
   private Integer fans=0,follows=0;

    public ProfileCount() {
    }

    public ProfileCount(String profileNo, int fans, int follows) {
        this.profileNo = profileNo;
        this.fans = fans;
        this.follows = follows;
    }

    public String getProfileNo() {
        return profileNo;
    }

    public void setProfileNo(String profileNo) {
        this.profileNo = profileNo;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(Integer follows) {
        this.follows = follows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfileCount that = (ProfileCount) o;

        if (profileNo != null ? !profileNo.equals(that.profileNo) : that.profileNo != null) return false;
        if (fans != null ? !fans.equals(that.fans) : that.fans != null) return false;
        return follows != null ? follows.equals(that.follows) : that.follows == null;
    }

    @Override
    public int hashCode() {
        int result = profileNo != null ? profileNo.hashCode() : 0;
        result = 31 * result + (fans != null ? fans.hashCode() : 0);
        result = 31 * result + (follows != null ? follows.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProfileCount{" +
            "profileNo='" + profileNo + '\'' +
            ", fans=" + fans +
            ", follows=" + follows +
            '}';
    }
}
