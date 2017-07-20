package com.enjoyf.platform.contentservice.feign;

import com.enjoyf.platform.contentservice.feign.domain.UserProfile;
import com.enjoyf.platform.contentservice.feign.domain.VerifyProfileDTO;
import com.enjoyf.platform.contentservice.feign.domain.WikiAppProfileDTO;
import com.enjoyf.platform.page.ScoreRangeRows;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

/**
 * Created by zhimingli on 2017/6/26.
 */
@FeignClient("profile-service")
public interface ProfileServiceFeignClient {

    @GetMapping("/api/wikiapp-profiles/recommend")
    ScoreRangeRows<WikiAppProfileDTO> recommend(@RequestParam("size") Integer size, @RequestParam(value = "flag", defaultValue = "-1") Double flag);

    @GetMapping("/api/verify-profiles/ids")
    Map<Long, VerifyProfileDTO> getProfilesByIds(@RequestParam(value = "ids") Set<Long> ids);

    @GetMapping("/api/verify-profiles/{id}")
    VerifyProfileDTO findOne(@RequestParam(value = "id") Long uid);

}
