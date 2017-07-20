package com.enjoyf.platform.profileservice.web.rest;

import com.enjoyf.platform.page.ScoreRange;
import com.enjoyf.platform.page.ScoreRangeRows;
import com.enjoyf.platform.page.ScoreSort;
import com.enjoyf.platform.profileservice.domain.VerifyProfile;
import com.enjoyf.platform.profileservice.service.WikiAppProfileService;
import com.enjoyf.platform.profileservice.service.contentservice.GameFeignClient;
import com.enjoyf.platform.profileservice.service.contentservice.dto.GameTagDTO;
import com.enjoyf.platform.profileservice.service.dto.WikiAppProfileDTO;
import com.enjoyf.platform.profileservice.web.rest.util.HeaderUtil;
import com.enjoyf.platform.profileservice.web.rest.util.PaginationUtil;
import com.enjoyf.platform.profileservice.web.rest.vm.WikiAppProfileVM;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.uid;

/**
 * Created by ericliu on 2017/6/24.
 */
@RestController
@RequestMapping("/api/wikiapp-profiles")
public class WikiAppUserInfoResource {

    private final Logger log = LoggerFactory.getLogger(VerifyProfileResource.class);

    private static final String ENTITY_NAME = "wikiAppUserInfo";

    private static final String RECOMMEND_ENTITY_NAME = "wikiAppRecommendUser";

    private final WikiAppProfileService wikiAppProfileService;
    private final GameFeignClient gameFeignClient;

    public WikiAppUserInfoResource(WikiAppProfileService wikiAppProfileService, GameFeignClient gameFeignClient) {
        this.wikiAppProfileService = wikiAppProfileService;
        this.gameFeignClient = gameFeignClient;
    }


    @PostMapping("/recommend")
    @ApiOperation(value = "添加到WIKIAPP的推荐用户", response = String.class)
    public ResponseEntity<String> save(@RequestParam Long uid) {
        if (log.isDebugEnabled()) {
            log.debug("finOneById id {},", uid);
        }

        wikiAppProfileService.saveRecommendUser(uid);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityCreationAlert(RECOMMEND_ENTITY_NAME, String.valueOf(uid)))
            .body("success");
    }


    @DeleteMapping("/recommend/{id}")
    @ApiOperation(value = "删除WIKIAPP的推荐用户", response = VerifyProfile.class)
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (log.isDebugEnabled()) {
            log.debug("finOneById id {},", id);
        }

        wikiAppProfileService.deleteRecommendUser(id);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityCreationAlert(RECOMMEND_ENTITY_NAME, String.valueOf(uid)))
            .body("success");
    }

    @GetMapping("/recommend")
    @ApiOperation(value = "获取WIKIAPP的推荐用户列表", response = VerifyProfile.class)
    public ResponseEntity<ScoreRangeRows<WikiAppProfileDTO>> findAll(@RequestParam("size") Integer size, @RequestParam(value = "flag", defaultValue = "-1") Double flag) {
        if (log.isDebugEnabled()) {
            log.debug("recommend findAll {},{}", size, flag);
        }

        ScoreRange scoreRange = new ScoreRange(-1, flag, size, ScoreSort.DESC);

        ScoreRangeRows<WikiAppProfileDTO> rows = wikiAppProfileService.findAllRecommendUser(scoreRange);
        return ResponseEntity.ok(rows);
    }

    @GetMapping("/recommend/page")
    @ApiOperation(value = "获取WIKIAPP的推荐用户列表", response = List.class)
    public ResponseEntity<List<WikiAppProfileDTO>> findAll(@ApiParam Pageable pageable) {
        if (log.isDebugEnabled()) {
            log.debug("recommend findAll {}", pageable);
        }

        Page<WikiAppProfileDTO> page = wikiAppProfileService.findAllRecommendUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/verify-profiles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID获取WikiApp个人信息，包括用户关注的游戏标签列表", response = WikiAppProfileDTO.class)
    public ResponseEntity<WikiAppProfileVM> finOneById(@PathVariable Long id) {
        if (log.isDebugEnabled()) {
            log.debug("finOneById id {},", id);
        }

        WikiAppProfileDTO dto = wikiAppProfileService.findOne(id);

        List<GameTagDTO> gameTagDTOList = gameFeignClient.findByUid(id);

        WikiAppProfileVM vm = new WikiAppProfileVM();
        vm.setGameTags(gameTagDTOList);
        vm.setProfile(dto);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vm));
    }

}
