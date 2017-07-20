package com.enjoyf.platform.contentservice.service;

import com.enjoyf.platform.contentservice.domain.Content;
import com.enjoyf.platform.contentservice.domain.contentsum.ContentSumType;
import com.enjoyf.platform.contentservice.domain.enumeration.ValidStatus;
import com.enjoyf.platform.contentservice.web.rest.vm.ContentVM;
import com.enjoyf.platform.contentservice.web.rest.vm.ContentGameVM;
import com.enjoyf.platform.page.ScoreRange;
import com.enjoyf.platform.page.ScoreRangeRows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Service Interface for managing Content.
 */
public interface ContentService {

    /**
     * Save a content.
     *
     * @param content the entity to save
     * @return the persisted entity
     */
    Content save(Content content);

    /**
     * Get all the contents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Content> findAll(Pageable pageable);

    /**
     * Get the "id" content.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Content findOne(Long id);


    Content findByCommentId(String commentId);

    /**
     * Delete the "id" content.
     *
     * @param id the id of the entity
     */
    void delete(Long id);


    ScoreRangeRows<ContentVM> findAllContentVM(String appkey, int platform, ScoreRange scoreRange, Long tagId, String profileId, int pnum);


    boolean updateContentStatus(String commentId, ValidStatus validStatus);


    Content postContentWiki(Content content);

    Content postContent(Content content);


    Map<Long, Content> queryContentByUserCollect(Set<Long> contentIds);

    boolean checkContentSum(Long contentId, ContentSumType contentSumType, String profileId);


    void hset(String field, String value);


    void sort(String firsttext, String nexttext);

    void hdel(String field);

    Map getContentSuggestTools();


    List<String> getContentSuggest();

    Page<ContentVM> searchContent(Pageable pageable, String text);


    Page<ContentGameVM> searchGame(Pageable pageable, String text);


    Map<Long, Content> queryContentByids(Set<Long> cotentidIdSet);
}
