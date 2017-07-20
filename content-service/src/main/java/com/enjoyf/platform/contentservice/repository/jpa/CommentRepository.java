package com.enjoyf.platform.contentservice.repository.jpa;

import com.enjoyf.platform.contentservice.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Spring Data JPA repository for the Comment entity.
 */
@SuppressWarnings("unused")
public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor {


    Page<Comment> findAllByGameIdAndValidStatus(long gameId, String validStuts, Pageable pageable);

    Comment findOneByGameIdAndUidAndValidStatus(long gameId, long uid, String validStatus);

    @Modifying
    @Query("update Comment c set c.agreeNum=c.agreeNum+1  where c.id=?1")
    int agreeCommentById(Long id);

    @Modifying
    @Query("update Comment c set c.replyNum=c.replyNum+1  where c.id=?1 ")
    int increaseReplyNum(Long id);

    @Modifying
    @Query("update Comment c set c.replyNum=c.replyNum-1  where c.id=?1 and c.replyNum>0")
    int reduceReplyNum(Long id);
    @Modifying
    @Query("update Comment c set c.validStatus=?1  where c.id=?2")
    int modifyCommentStatus(String valid, Long id);

}
