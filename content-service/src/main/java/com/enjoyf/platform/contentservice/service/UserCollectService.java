package com.enjoyf.platform.contentservice.service;

import com.enjoyf.platform.contentservice.domain.UserCollect;
import com.enjoyf.platform.page.PageRows;
import com.enjoyf.platform.page.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * Service Interface for managing UserCollect.
 */
public interface UserCollectService {

    /**
     * Save a userCollect.
     *
     * @param userCollect the entity to save
     * @return the persisted entity
     */
    UserCollect save(UserCollect userCollect);

    /**
     * Get all the userCollects.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserCollect> findAll(Pageable pageable);

    /**
     * Get the "id" userCollect.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UserCollect findOne(Long id);


    UserCollect findOneByContentId(Long contentId);

    /**
     * GET usercollect 通过pid和文章ID查询是否收藏过
     * @param profileId
     * @param contentId
     * @return
     */
    UserCollect findByProfileIdAndContentId(String profileId, long contentId);


    /**
     * Delete the "id" userCollect.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    boolean deleteUserCollect(Set<Long> idSet, String profileId);

    PageRows<UserCollect> queryCollectByCache(String profileId, Pagination pagination);
}
