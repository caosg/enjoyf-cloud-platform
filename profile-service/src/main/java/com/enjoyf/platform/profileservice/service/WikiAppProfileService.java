package com.enjoyf.platform.profileservice.service;

import com.enjoyf.platform.page.ScoreRange;
import com.enjoyf.platform.page.ScoreRangeRows;
import com.enjoyf.platform.profileservice.service.dto.WikiAppProfileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by ericliu on 2017/6/24.
 */
public interface WikiAppProfileService {

    WikiAppProfileDTO findOne(Long uid);

    /**
     * @param uid
     */
    void saveRecommendUser(Long uid);

    /**
     * @param uid
     * @return
     */
    boolean deleteRecommendUser(Long uid);

    /**
     * @param scoreRange
     * @return
     */
    ScoreRangeRows<WikiAppProfileDTO> findAllRecommendUser(ScoreRange scoreRange);

    Page<WikiAppProfileDTO> findAllRecommendUser(Pageable pageable);

}
