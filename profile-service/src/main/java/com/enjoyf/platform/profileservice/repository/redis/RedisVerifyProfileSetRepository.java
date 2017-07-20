package com.enjoyf.platform.profileservice.repository.redis;

import com.enjoyf.platform.page.ScoreRange;
import com.enjoyf.platform.page.ScoreSort;
import com.enjoyf.platform.profileservice.domain.VerifyProfile;
import com.enjoyf.platform.profileservice.domain.enumeration.VerifyProfileType;
import com.hazelcast.util.CollectionUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by ericliu on 2017/6/15.
 */
@Component
public class RedisVerifyProfileSetRepository extends AbstractRepository {


    private static final String PREFIX_VERIFY_PROFILE = "profileservice:verifyprofileset:";

    private static final String ZSET_VERIFY_PROFILE = PREFIX_VERIFY_PROFILE + "zset";


    public RedisVerifyProfileSetRepository(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    public void addVerifyProfile(VerifyProfile verifyProfile) {
        redisTemplate.opsForZSet().add(getVerifyProfileZsetKey(verifyProfile.getVerifyType()), String.valueOf(verifyProfile.getId()), verifyProfile.getId());
    }

    public Set<Long> findAllByVerifyType(VerifyProfileType verifyType, ScoreRange scoreRange) {
        Set<String> set = zrangeByScore(getVerifyProfileZsetKey(verifyType), scoreRange);

        Set<Long> idSet = new LinkedHashSet<>();
        for (String id : set) {
            try {
                idSet.add(Long.valueOf(id));
            } catch (NumberFormatException e) {
            }
        }

        return idSet;
    }

    public boolean removeId(VerifyProfileType verifyType, Long id) {
        return redisTemplate.opsForZSet().remove(getVerifyProfileZsetKey(verifyType), String.valueOf(id)) > 0;
    }

    private String getVerifyProfileZsetKey(VerifyProfileType verifyType) {
        return ZSET_VERIFY_PROFILE + verifyType.name();
    }

    public Set<String> zrangeByScore(String key, ScoreRange scoreRange) {
        if (scoreRange.getMin() < 0.0) {
            Set<ZSetOperations.TypedTuple<String>> minElement = scoreRange.getSort().equals(ScoreSort.DESC) ?
                redisTemplate.opsForZSet().reverseRangeWithScores(key, -1, -1) :
                redisTemplate.opsForZSet().rangeWithScores(key, 0, 0);
            if (!CollectionUtil.isEmpty(minElement)) {
                scoreRange.setMin(minElement.iterator().next().getScore());
            }
        }


        if (scoreRange.getMax() < 0.0) {
            Set<ZSetOperations.TypedTuple<String>> maxElement = scoreRange.getSort().equals(ScoreSort.DESC) ?
                redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, 0) :
                redisTemplate.opsForZSet().rangeWithScores(key, -1, -1);
            if (!CollectionUtil.isEmpty(maxElement)) {
                scoreRange.setMax(maxElement.iterator().next().getScore());
            }
        }


        String minExpress = String.valueOf(scoreRange.getMin());
        String maxExpress = String.valueOf(scoreRange.getMax());
        if (scoreRange.getSort().equals(ScoreSort.DESC)) {
            maxExpress = String.valueOf((scoreRange.isFirstPage() ? scoreRange.getMax() + 1 : scoreRange.getMax() - 1));
        } else {
            minExpress = String.valueOf((scoreRange.isFirstPage() ? scoreRange.getMin() - 1 : scoreRange.getMin() + 1));
        }

        Set<ZSetOperations.TypedTuple<String>> tuples = scoreRange.getSort().equals(ScoreSort.DESC) ?
            redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, Double.valueOf(minExpress), Double.valueOf(maxExpress), scoreRange.getLimit(), scoreRange.getSize()) :
            redisTemplate.opsForZSet().rangeByScoreWithScores(key, Double.valueOf(minExpress), Double.valueOf(maxExpress), scoreRange.getLimit(), scoreRange.getSize());


        Set<String> result = new LinkedHashSet<String>();
        int i = 0;
        for (ZSetOperations.TypedTuple tuple : tuples) {
            if (i == tuples.size() - 1) {
                double flag = tuple.getScore();
                scoreRange.setScoreflag(flag);
            }
            result.add((String) tuple.getValue());
            i++;
        }

        boolean hasNext = scoreRange.getSort().equals(ScoreSort.DESC) ? scoreRange.getScoreflag() > scoreRange.getMin()
            : scoreRange.getScoreflag() < scoreRange.getMax();
        scoreRange.setHasnext(CollectionUtil.isEmpty(result) ? false : hasNext);

        return result;
    }

}

