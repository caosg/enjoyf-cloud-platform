package com.enjoyf.platform.userservice.repository.redis;

import com.enjoyf.platform.userservice.domain.UserSettings;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by shuguangcao on 2017/5/15.
 */
public interface RedisUserSettingsRepository extends CrudRepository<UserSettings,Long> {
    UserSettings findOneByProfileNo(String profileNo);
}
