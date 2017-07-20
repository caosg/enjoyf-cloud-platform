package com.enjoyf.platform.userservice.repository.redis;

import com.enjoyf.platform.userservice.domain.redis.ProfileCount;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by shuguangcao on 2017/4/10.
 */
public interface RedisProfileCountRepository extends CrudRepository<ProfileCount,String>{

    List<ProfileCount> findByProfileNoIn(Collection<String> profileNos);
}
