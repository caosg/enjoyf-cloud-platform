package com.enjoyf.platform.contentservice.repository.jpa;

import com.enjoyf.platform.contentservice.domain.ContentTag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ContentTag entity.
 */
@SuppressWarnings("unused")
public interface ContentTagRepository extends JpaRepository<ContentTag, Long> {
    Page<ContentTag> findAllByTagLine(String tagLine, Pageable pageable);
}
