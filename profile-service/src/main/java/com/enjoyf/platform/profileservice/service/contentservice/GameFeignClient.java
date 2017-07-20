
package com.enjoyf.platform.profileservice.service.contentservice;

import com.enjoyf.platform.profileservice.service.contentservice.dto.GameTagDTO;
import com.enjoyf.platform.profileservice.service.contentservice.dto.UserCommentSumDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * List
 * Created by ericliu on 2017/6/12.
 */
@FeignClient("content-service")
public interface GameFeignClient {

    @GetMapping("/api/game-tags/uid/{uid}")
    List<GameTagDTO> findByUid(@PathVariable("uid") Long uid);
}
