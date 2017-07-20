package com.enjoyf.platform.messageservice.service.userservice;

import com.enjoyf.platform.messageservice.service.userservice.domain.UserProfileDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by ericliu on 2017/6/12.
 */
@FeignClient("user-service")
public interface UserProfileFeignClient {

    @GetMapping("/api/user-profiles/{id}")
    UserProfileDTO findOne(@RequestParam(value = "id") Long uid);

    @GetMapping("/api/user-profiles/ids")
    List<UserProfileDTO> findUserProfilesByUids(@RequestParam(value = "ids") Long[] uids);
}
