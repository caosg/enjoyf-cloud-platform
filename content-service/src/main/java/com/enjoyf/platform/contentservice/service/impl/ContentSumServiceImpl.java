package com.enjoyf.platform.contentservice.service.impl;

import com.enjoyf.platform.contentservice.domain.contentsum.ContentSumType;
import com.enjoyf.platform.contentservice.repository.redis.AskRedisRepository;
import com.enjoyf.platform.contentservice.service.ContentSumService;
import com.enjoyf.platform.contentservice.domain.contentsum.ContentSum;
import com.enjoyf.platform.contentservice.repository.jpa.ContentSumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service Implementation for managing ContentSum.
 */
@Service
@Transactional
public class ContentSumServiceImpl implements ContentSumService {

    private final Logger log = LoggerFactory.getLogger(ContentSumServiceImpl.class);

    private final ContentSumRepository contentSumRepository;

    private final AskRedisRepository askRedisRepository;


    public ContentSumServiceImpl(ContentSumRepository contentSumRepository, AskRedisRepository askRedisRepository) {
        this.contentSumRepository = contentSumRepository;
        this.askRedisRepository = askRedisRepository;
    }

    /**
     * Save a contentSum.
     *
     * @param contentSum the entity to save
     * @return the persisted entity
     */
    @Override
    public ContentSum save(ContentSum contentSum) {
        log.debug("Request to save ContentSum : {}", contentSum);
        ContentSum result = contentSumRepository.save(contentSum);
        return result;
    }

    /**
     * Get all the contentSums.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContentSum> findAll() {
        log.debug("Request to get all ContentSums");
        List<ContentSum> result = contentSumRepository.findAll();

        return result;
    }

    /**
     * Get one contentSum by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ContentSum findOne(Long id) {
        log.debug("Request to get ContentSum : {}", id);
        ContentSum contentSum = askRedisRepository.getContentSumById(id);
        if (contentSum == null) {
            contentSum = contentSumRepository.findOne(id);
        }
        return contentSum;
    }

    /**
     * Delete the  contentSum by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContentSum : {}", id);
        contentSumRepository.delete(id);
    }

    @Override
    public boolean addContentSum(Long contentId, ContentSumType contentSumType, String profileId) {
        log.debug("Request to addContentSum : {},{},{}", contentId, contentSumType, profileId);
        boolean bval = askRedisRepository.checkContentSum(contentId, contentSumType, profileId);
        if (bval) {
            return false;
        }

        bval = contentSumRepository.increaseContentSumAgreeNum(1, contentId) > 0;
        if (!bval) {
            ContentSum sum = new ContentSum();
            sum.setCreateDate(new Date());
            sum.setId(contentId);
            if (contentSumType.equals(contentSumType.AGREE_NUM)) {
                sum.setAgree_num(1);
            }
            contentSumRepository.save(sum);
            bval = true;
        }
        if (bval) {
            bval = askRedisRepository.increaseContentSum(contentId, contentSumType, 1, profileId);
        }
        return bval;
    }


    @Override
    public Map<Long, ContentSum> queryContentSumByids(Set<Long> contentIds) {
        log.debug("Request to queryContentSumByids : {},{},{}", contentIds);
        Map<Long, ContentSum> returnMap = new LinkedHashMap<Long, ContentSum>();
        for (Long contengId : contentIds) {
            ContentSum contentSum = findOne(contengId);
            if (contentSum != null) {

                returnMap.put(contengId, contentSum);
            }
        }
        return returnMap;
    }
}
