package com.enjoyf.platform.contentservice.service;

import com.enjoyf.platform.contentservice.domain.UserCommentSum;
import com.enjoyf.platform.contentservice.domain.enumeration.UserCommentSumFiled;

import java.util.List;
import java.util.Map;

/**
 * Service Interface for managing UserCommentSum.
 */
public interface UserCommentSumService {

    /**
     * Save a userCommentSum.
     *
     * @param the entity to save
     * @return the persisted entity
     */
    long increase(long uid, UserCommentSumFiled filed, long value);



    /**
     *  Get the "id" userCommentSum.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UserCommentSum findOne(Long id);


    Map<Long,UserCommentSum> findAllByIds(Long... uids);


}
