package com.enjoyf.platform.contentservice.repository.jpa;

import com.enjoyf.platform.contentservice.domain.UserCollect;

import com.enjoyf.platform.page.Pagination;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the UserCollect entity.
 */
@SuppressWarnings("unused")
public interface UserCollectRepository extends JpaRepository<UserCollect, Long> {
    UserCollect findOneByContentId(long contentId);

    UserCollect findByProfileIdAndContentId(String profileId, long contentId);

    @Modifying
    @Query("delete from UserCollect where id in ?1")
    int deleteUserCollectByIds(Set<Long> idSet);


    List<UserCollect> findByIdIn(Set<Long> queryIds);
}
