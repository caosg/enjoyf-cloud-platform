package com.enjoyf.platform.userservice.repository.redis;

import com.enjoyf.platform.userservice.domain.UserProfile;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by shuguangcao on 2017/3/27.
 */
public interface RedisUserProfileRepository extends CrudRepository<UserProfile,Long> {

    UserProfile findOneByProfileNo(String profileNo);

    UserProfile findOneBylowercaseNick(String nick);
}
