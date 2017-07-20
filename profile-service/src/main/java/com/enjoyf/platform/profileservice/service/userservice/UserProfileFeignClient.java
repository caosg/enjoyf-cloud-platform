package com.enjoyf.platform.profileservice.service.userservice;

import com.enjoyf.platform.profileservice.service.userservice.dto.UserProfileDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ericliu on 2017/6/12.
 */
@FeignClient("user-service")
public interface UserProfileFeignClient {

    @PostMapping("/api/user-profiles")
    UserProfileDTO saveUserProfile(@RequestBody UserProfileDTO userProfileDTO);

    @GetMapping("/api/user-profiles/{id}")
    UserProfileDTO findOne(@RequestParam(value = "id") Long uid);

    @PutMapping("/api/user-profiles")
    UserProfileDTO update(@RequestBody UserProfileDTO userProfileDTO);

    @GetMapping("/api/user-profiles/ids")
    List<UserProfileDTO> findUserProfilesByUids(@RequestParam(value = "ids") Long[] uids);
}
