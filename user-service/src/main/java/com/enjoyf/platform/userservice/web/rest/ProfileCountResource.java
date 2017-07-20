package com.enjoyf.platform.userservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.userservice.domain.redis.ProfileCount;
import com.enjoyf.platform.userservice.repository.redis.RedisProfileCountRepository;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * Created by shuguangcao on 2017/4/10.
 */
@RestController
@RequestMapping("/api")
@Api(value = "用户关注粉丝数")
public class ProfileCountResource {
    private final Logger log = LoggerFactory.getLogger(ProfileCountResource.class);

    private final RedisProfileCountRepository redisProfileCountRepository;

    public ProfileCountResource(RedisProfileCountRepository redisProfileCountRepository) {
        this.redisProfileCountRepository = redisProfileCountRepository;
    }

    @PostMapping("/profile-counts")
    @Timed
    public ResponseEntity<ProfileCount> createProfileCount(@Valid @RequestBody ProfileCount profileCount){
        redisProfileCountRepository.save(profileCount);
        return ResponseEntity.ok(profileCount);
    }

    @PutMapping("/profile-counts/increment")
    public ResponseEntity<Integer> increment(@RequestParam String profileNo, @RequestParam int delta , @RequestParam String field){
        Integer result = 0;
        ProfileCount profileCount = redisProfileCountRepository.findOne(profileNo);
        if(profileCount==null){
            profileCount = new ProfileCount();
            profileCount.setProfileNo(profileNo);
            if(field.equals("fans"))
                profileCount.setFans(delta);
            if(field.equals("follows"))
                profileCount.setFollows(delta);

            result = delta;
        }else {
            if(field.equals("fans")) {
                profileCount.setFans(profileCount.getFans() + delta);
                result = profileCount.getFans();
            }
            if(field.equals("follows")) {
                profileCount.setFollows(profileCount.getFollows() + delta);
                result = profileCount.getFollows();
            }
        }
        redisProfileCountRepository.save(profileCount);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/profile-counts/{profileNo}")
    @Timed
    public ResponseEntity<ProfileCount> getProfileCount(@PathVariable String profileNo){
        ProfileCount profileCount = redisProfileCountRepository.findOne(profileNo);
        if(profileCount==null)
            profileCount = new ProfileCount(profileNo,0,0);
        return ResponseEntity.ok(profileCount);
    }

    @GetMapping("/profile-counts")
    @Timed
    public ResponseEntity<List<ProfileCount>> getProfileCounts(@RequestParam Set<String> profileNos){
       List<ProfileCount> result = redisProfileCountRepository.findByProfileNoIn(profileNos);
       return ResponseEntity.ok(result);
    }
}
