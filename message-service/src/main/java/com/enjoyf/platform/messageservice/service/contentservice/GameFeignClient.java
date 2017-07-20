
package com.enjoyf.platform.messageservice.service.contentservice;

import com.enjoyf.platform.messageservice.service.contentservice.domain.Game;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by ericliu on 2017/6/12.
 */
@FeignClient("content-service")
public interface GameFeignClient {


    @GetMapping("/api/game/{id}")
    Game findOne(@RequestParam(value = "id") Long uid);

    @GetMapping("/api/game/ids")
    Map<Long,Game> findByIds(@RequestParam(value = "ids") Long[] uids);
}
