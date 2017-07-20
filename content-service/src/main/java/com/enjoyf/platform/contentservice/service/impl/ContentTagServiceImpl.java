package com.enjoyf.platform.contentservice.service.impl;

import com.enjoyf.platform.contentservice.domain.ContentTag;
import com.enjoyf.platform.contentservice.domain.enumeration.ContentTagLine;
import com.enjoyf.platform.contentservice.domain.enumeration.ValidStatus;
import com.enjoyf.platform.contentservice.repository.jpa.ContentTagRepository;
import com.enjoyf.platform.contentservice.repository.redis.AskRedisRepository;
import com.enjoyf.platform.contentservice.service.ContentTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Service Implementation for managing ContentTag.
 */
@Service
@Transactional
public class ContentTagServiceImpl implements ContentTagService {

    private final Logger log = LoggerFactory.getLogger(ContentTagServiceImpl.class);

    private final ContentTagRepository contentTagRepository;

    private final AskRedisRepository askRedisRepository;

    public ContentTagServiceImpl(ContentTagRepository contentTagRepository, AskRedisRepository askRedisRepository) {
        this.contentTagRepository = contentTagRepository;
        this.askRedisRepository = askRedisRepository;
    }

    /**
     * Save a contentTag.
     *
     * @param contentTag the entity to save
     * @return the persisted entity
     */
    @Override
    public ContentTag save(ContentTag contentTag) {
        log.debug("Request to save ContentTag : {}", contentTag);
        ContentTag result = contentTagRepository.save(contentTag);
        askRedisRepository.setContentTag(result);


        if (result.getValidStatus().equals(ValidStatus.VALID.getCode())) {
            askRedisRepository.addContentTag(contentTag.getTagLine(), result);
        } else {
            askRedisRepository.removeContentTag(contentTag.getTagLine(), contentTag.getId());
        }
        return result;
    }

    /**
     * Get all the contentTags.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContentTag> findAllByTagLine(String tagLine, Pageable pageable) {
        log.debug("Request to get all ContentTags");
        return contentTagRepository.findAllByTagLine(tagLine, pageable);
    }


    @Override
    public List<ContentTag> findAll() {
        log.debug("Request to get all ContentTags");
        return contentTagRepository.findAll();
    }


    @Override
    public List<ContentTag> queryContentTagByTagLine(ContentTagLine contentTagLine) {
        log.debug("Request to get all ContentTags:{}", contentTagLine);
        Set<String> contentTag = askRedisRepository.zrangeContentTag(contentTagLine.getCode());
        List<ContentTag> returnList = new ArrayList<ContentTag>();
        for (String strid : contentTag) {
            ContentTag tag = findOne(Long.valueOf(strid));
            if (tag != null && tag.getValidStatus().equals(ValidStatus.VALID.getCode())) {
                returnList.add(tag);
            }
        }
        return returnList;
    }

    /**
     * Get one contentTag by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ContentTag findOne(Long id) {
        log.debug("Request to get ContentTag : {}", id);
        ContentTag contentTag = askRedisRepository.getContentTag(id);
        if (contentTag == null) {
            contentTag = contentTagRepository.findOne(id);
            if (contentTag != null) {
                askRedisRepository.setContentTag(contentTag);
            }
        }
        return contentTag;
    }

    /**
     * Delete the  contentTag by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContentTag : {}", id);
        ContentTag result = findOne(id);
        contentTagRepository.delete(id);
        askRedisRepository.delContent(id);
        askRedisRepository.removeContentTag(result.getTagLine(), result.getId());
    }
}
