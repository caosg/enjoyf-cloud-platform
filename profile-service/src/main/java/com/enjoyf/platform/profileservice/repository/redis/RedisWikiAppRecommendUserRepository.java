package com.enjoyf.platform.profileservice.repository.redis;

import com.enjoyf.platform.page.ScoreRange;
import com.enjoyf.platform.page.ScoreSort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by ericliu on 2017/6/26.
 */
@Component
public class RedisWikiAppRecommendUserRepository extends AbstractRepository {

    private static final String PREFIX_VERIFY_PROFILE = "profileservice:wikiapprecommend:";

    private static final String ZSET_VERIFY_PROFILE = PREFIX_VERIFY_PROFILE + "zset";


    public RedisWikiAppRecommendUserRepository(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    public void insertRecommendUser(Long uid) {
        redisTemplate.opsForZSet().add(ZSET_VERIFY_PROFILE, String.valueOf(uid), praseScore(uid));
    }

    public boolean delRecommendUser(Long uid) {
        return redisTemplate.opsForZSet().remove(ZSET_VERIFY_PROFILE, String.valueOf(uid)) > 0;
    }

    public List<Long> findAll(ScoreRange scoreRange) {
        List<Long> idSet = new ArrayList<>();
        zrangeByScore(ZSET_VERIFY_PROFILE, scoreRange).stream().forEach(idString -> {
            try {
                idSet.add(Long.valueOf(idString));
            } catch (NumberFormatException e) {
                return;
            }
        });

        return idSet;
    }

    public Page<Long> findAll(Pageable pageable) {
        return findAll(ZSET_VERIFY_PROFILE, pageable, ScoreSort.DESC);
    }

    private Double praseScore(Long uid) {
        return Double.parseDouble(System.currentTimeMillis() + "." + uid + "1");
    }
}
