package com.enjoyf.platform.contentservice.service.impl;

import com.enjoyf.platform.contentservice.domain.UserCommentSum;
import com.enjoyf.platform.contentservice.domain.enumeration.UserCommentSumFiled;
import com.enjoyf.platform.contentservice.repository.redis.RedisUserCommentSumRepository;
import com.enjoyf.platform.contentservice.service.UserCommentSumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.PartialUpdate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service Implementation for managing UserCommentSum.
 */
@Service
public class UserCommentSumServiceImpl implements UserCommentSumService {

    private final Logger log = LoggerFactory.getLogger(UserCommentSumServiceImpl.class);

    private final RedisUserCommentSumRepository userCommentSumRepository;

    public UserCommentSumServiceImpl(RedisUserCommentSumRepository userCommentSumRepository) {
        this.userCommentSumRepository = userCommentSumRepository;
    }

    @Override
    public long increase(long uid, UserCommentSumFiled filed, long value) {
        return userCommentSumRepository.increase(uid, filed, value);
    }


    /**
     * Get one userCommentSum by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public UserCommentSum findOne(Long id) {
        log.debug("Request to get UserCommentSum : {}", id);
        UserCommentSum userCommentSum = userCommentSumRepository.getUserCommentSum(id);
        return userCommentSum;
    }

    @Override
    public Map<Long, UserCommentSum> findAllByIds(Long... uids) {
        log.debug("Request to findAllByIds : {}", uids);
        Map<Long, UserCommentSum> result = new HashMap<>();
        for (Long uid : uids) {
            UserCommentSum userCommentSum = findOne(uid);
            if (userCommentSum != null) {
                result.put(uid,userCommentSum);
            }
        }
        return result;
    }
}
