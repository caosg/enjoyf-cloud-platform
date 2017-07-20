package com.enjoyf.platform.contentservice.service.impl;

import com.enjoyf.platform.common.util.CollectionUtil;
import com.enjoyf.platform.contentservice.repository.redis.AskRedisRepository;
import com.enjoyf.platform.contentservice.service.UserCollectService;
import com.enjoyf.platform.contentservice.domain.UserCollect;
import com.enjoyf.platform.contentservice.repository.jpa.UserCollectRepository;
import com.enjoyf.platform.page.PageRows;
import com.enjoyf.platform.page.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service Implementation for managing UserCollect.
 */
@Service
@Transactional
public class UserCollectServiceImpl implements UserCollectService {

    private final Logger log = LoggerFactory.getLogger(UserCollectServiceImpl.class);

    private final UserCollectRepository userCollectRepository;

    private final AskRedisRepository askRedisRepository;

    public UserCollectServiceImpl(UserCollectRepository userCollectRepository, AskRedisRepository askRedisRepository) {
        this.userCollectRepository = userCollectRepository;
        this.askRedisRepository = askRedisRepository;
    }

    /**
     * Save a userCollect.
     *
     * @param userCollect the entity to save
     * @return the persisted entity
     */
    @Override
    public UserCollect save(UserCollect userCollect) {
        log.debug("Request to save UserCollect : {}", userCollect);
        UserCollect result = userCollectRepository.save(userCollect);
        if (result.getId() > 0) {
            askRedisRepository.addUserCollectList(userCollect);
        }
        return result;
    }

    /**
     * Get all the userCollects.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserCollect> findAll(Pageable pageable) {
        log.debug("Request to get all UserCollects");
        Page<UserCollect> result = userCollectRepository.findAll(pageable);
        return result;
    }

    /**
     * Get one userCollect by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserCollect findOne(Long id) {
        log.debug("Request to get UserCollect : {}", id);
        UserCollect userCollect = userCollectRepository.findOne(id);
        return userCollect;
    }

    @Override
    public UserCollect findOneByContentId(Long contentId) {
        log.debug("Request to get UserCollect BY contentId: {}", contentId);
        return userCollectRepository.findOneByContentId(contentId);
    }

    @Override
    public UserCollect findByProfileIdAndContentId(String profileId, long contentId) {
        log.debug("Request to get UserCollect findByProfileIdAndContentId: {},{}", contentId, profileId);
        return userCollectRepository.findByProfileIdAndContentId(profileId, contentId);
    }


    @Override
    public boolean deleteUserCollect(Set<Long> idSet, String profileId) {
        log.debug("Request to get deleteUserCollect BY idSet: {} ,{}", idSet.toString(), profileId);

        boolean bool = false;
        if (!CollectionUtil.isEmpty(idSet)) {
            bool = userCollectRepository.deleteUserCollectByIds(idSet) > 0;
        }
        if (bool) {
            for (Long id : idSet) {
                //把成功从缓存清除的数据放入List 在数据库进行物理删除
                if (askRedisRepository.removeUserCollectList(profileId, String.valueOf(id))) {
                    askRedisRepository.delUserCollect(id);
                }
            }
        }
        return bool;
    }

    @Override
    public PageRows<UserCollect> queryCollectByCache(String profileId, Pagination pagination) {
        log.debug("Request to get queryCollectByCache BY idSet: {} ,{}", profileId, pagination);

        Set<String> ids = askRedisRepository.queryUserCollectList(profileId, pagination, true);
        if (CollectionUtil.isEmpty(ids)) {
            return null;
        }
        PageRows<UserCollect> pageRows = new PageRows<UserCollect>();
        int sum = (int) askRedisRepository.getUserCollectNum(profileId); //获得总数
        pagination.setTotalRows(sum);
        pageRows.setPage(pagination);

        Set<Long> idsLong = new LinkedHashSet<Long>();
        for (String id : ids) {
            idsLong.add(Long.parseLong(id));
        }
        Map<Long, UserCollect> map = queryUserCollect(idsLong);//查询游戏表信息
        List<UserCollect> list = new ArrayList<UserCollect>();
        for (long id : idsLong) {
            if (map.get(id) != null) {
                list.add(map.get(id));
            }
        }
        pageRows.setRows(list);
        return pageRows;
    }

    private Map<Long, UserCollect> queryUserCollect(Set<Long> ids)  {
        Map<Long, UserCollect> map = new HashMap<Long, UserCollect>();

        Set<Long> queryIds = new HashSet<Long>();
        for (Long id : ids) {
            UserCollect userCollect = askRedisRepository.getUserCollect(id);
            if (userCollect == null) {
                queryIds.add(id);
            } else {
                map.put(id, userCollect);
            }
        }
        if (!CollectionUtil.isEmpty(queryIds)) {
            List<UserCollect> userCollectList = userCollectRepository.findByIdIn(queryIds);
            if (!CollectionUtil.isEmpty(userCollectList)) {
                for (UserCollect userCollect : userCollectList) {
                    askRedisRepository.setUserCollect(userCollect);
                    map.put(userCollect.getId(), userCollect);
                }
            }
        }

        return map;
    }

    /**
     * Delete the  userCollect by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserCollect : {}", id);
        userCollectRepository.delete(id);
    }
}
