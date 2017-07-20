
package com.enjoyf.platform.profileservice.service.contentservice;

import com.enjoyf.platform.profileservice.service.contentservice.dto.UserCommentSumDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by ericliu on 2017/6/12.
 */
@FeignClient("content-service")
public interface UserCommentSumFeignClient {


    @GetMapping("/api/user-comment-sums/{id}")
    UserCommentSumDTO findOne(@PathVariable(value = "id") Long uid);


    @GetMapping("/api/user-comment-sums/ids")
    Map<Long, UserCommentSumDTO> findByIds(@RequestParam(value = "ids") Long[] uids);
}
