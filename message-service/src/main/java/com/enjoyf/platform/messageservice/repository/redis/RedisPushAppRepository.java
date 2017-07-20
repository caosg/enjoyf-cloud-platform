package com.enjoyf.platform.messageservice.repository.redis;

import com.enjoyf.platform.messageservice.domain.PushApp;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ericliu on 2017/6/9.
 */
public interface RedisPushAppRepository extends CrudRepository<PushApp, Long> {

    PushApp findOneByAppkey(String appKey);
}
