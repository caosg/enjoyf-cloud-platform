package com.enjoyf.platform.contentservice.repository.redis;

import com.enjoyf.platform.contentservice.domain.GameTag;
import com.enjoyf.platform.contentservice.domain.UserCommentSum;
import com.enjoyf.platform.contentservice.domain.enumeration.UserCommentSumFiled;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhimingli on 2017/6/20.
 */
@Component
public class RedisUserCommentSumRepository {

    private final String PREFIX_HASH_COMMENTSUM_ = "contentservice:usercommentsum:";
    private final StringRedisTemplate redisTemplate;

    public RedisUserCommentSumRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public UserCommentSum getUserCommentSum(long uid) {
        UserCommentSum userCommentSum = new UserCommentSum();
        userCommentSum.setUid(uid);
        List list = new ArrayList<>();
        list.add(UserCommentSumFiled.USEFUL.name());
        list.add(UserCommentSumFiled.COMMENT.name());
        List valueList = redisTemplate.opsForHash().multiGet(getCommentSumKey(uid), list);
        userCommentSum.setUsefulSum(valueList.get(0) == null ? 0 : Long.parseLong((String)valueList.get(0)) );
        userCommentSum.setCommentSum(valueList.get(1) == null ? 0 : Long.parseLong((String)valueList.get(1)));
        return userCommentSum;
    }


    public long increase(long uid, UserCommentSumFiled filed, Long value) {
        return redisTemplate.opsForHash().increment(getCommentSumKey(uid), filed.name(), value);
    }


    private String getCommentSumKey(long uid) {
        return PREFIX_HASH_COMMENTSUM_ + uid;
    }

}
