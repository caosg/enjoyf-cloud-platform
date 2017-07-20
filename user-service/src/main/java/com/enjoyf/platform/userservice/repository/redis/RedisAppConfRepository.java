package com.enjoyf.platform.userservice.repository.redis;

import com.enjoyf.platform.userservice.domain.AppConf;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by shuguangcao on 2017/7/4.
 */
public interface RedisAppConfRepository extends CrudRepository<AppConf,Long>{
   AppConf findOneByAppKey(String appKey);
}
