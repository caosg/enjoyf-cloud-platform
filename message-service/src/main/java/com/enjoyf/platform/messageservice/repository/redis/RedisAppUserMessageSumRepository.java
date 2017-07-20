package com.enjoyf.platform.messageservice.repository.redis;

import com.enjoyf.platform.messageservice.domain.AppUserMessageSum;
import com.google.common.base.Strings;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by ericliu on 2017/6/24.
 */
@Component
public class RedisAppUserMessageSumRepository extends AbstractRedisRepository {
    private final String PREFIX_HASH_USERMESSAGE_SET_KEY_ = "messageservice:appusermessagesum:";

    public RedisAppUserMessageSumRepository(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }


    private String getKey(String appkey, long uid) {
        return PREFIX_HASH_USERMESSAGE_SET_KEY_ + ":" + appkey + ":" + uid;
    }

    public void increaseMessageSum(String appkey, long uid, int sum) {
        redisTemplate.opsForValue().increment(getKey(appkey, uid), sum);
    }

    public AppUserMessageSum getMessageSum(String appkey, long uid) {
        String valueByRedis = redisTemplate.opsForValue().get(getKey(appkey, uid));

        long value = 0;
        if (!Strings.isNullOrEmpty(valueByRedis)) {
            value = Long.parseLong(valueByRedis);
        }
        AppUserMessageSum appUserMessageSum = new AppUserMessageSum();
        appUserMessageSum.setAppKey(appkey);
        appUserMessageSum.setUid(uid);
        appUserMessageSum.setSum(value);

        return appUserMessageSum;
    }
}
