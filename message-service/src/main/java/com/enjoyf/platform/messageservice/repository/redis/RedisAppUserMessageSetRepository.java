package com.enjoyf.platform.messageservice.repository.redis;

import com.enjoyf.platform.messageservice.domain.AppUserMessage;
import com.enjoyf.platform.page.ScoreRange;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Spring Data JPA repository for the ProfileRating entity.
 */
@Component
public class RedisAppUserMessageSetRepository extends AbstractRedisRepository {

    private final String PREFIX_HASH_USERMESSAGE_SET_KEY_ = "messageservice:appusermessagezset:";


    public RedisAppUserMessageSetRepository(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }


    public void saddMessageApp(AppUserMessage appUserMessage) {
        redisTemplate.opsForZSet().add(getDeviceKey(appUserMessage.getAppkey(), appUserMessage.getUid()), String.valueOf(appUserMessage.getId()), appUserMessage.getId());
    }

    public List<Long> findMessageIds(String appkey, long uid, ScoreRange scoreRange) {
        Set<String> messageIdStrings = zrangeByScore(getDeviceKey(appkey, uid), scoreRange);

        if (CollectionUtils.isEmpty(messageIdStrings)) {
            return new ArrayList<>();
        }
        return messageIdStrings.stream().map(Long::valueOf).collect(Collectors.toList());
    }

    public void removeMessageId(String appkey, long uid, long messageId) {
        redisTemplate.opsForZSet().remove(getDeviceKey(appkey, uid), String.valueOf(messageId));
    }

    private String getDeviceKey(String appkey, long uid) {
        return PREFIX_HASH_USERMESSAGE_SET_KEY_ + ":" + appkey + ":" + uid;
    }
}
