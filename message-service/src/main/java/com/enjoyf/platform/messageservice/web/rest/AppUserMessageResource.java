package com.enjoyf.platform.messageservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.autoconfigure.context.CommonContextHolder;
import com.enjoyf.platform.autoconfigure.security.EnjoySecurityUtils;
import com.enjoyf.platform.autoconfigure.web.error.BusinessException;
import com.enjoyf.platform.messageservice.domain.AppUserMessage;
import com.enjoyf.platform.messageservice.domain.AppUserMessageSum;
import com.enjoyf.platform.messageservice.service.AppUserMessageService;
import com.enjoyf.platform.messageservice.service.dto.AppUserMessageDTO;
import com.enjoyf.platform.messageservice.web.rest.util.HeaderUtil;
import com.enjoyf.platform.page.ScoreRange;
import com.enjoyf.platform.page.ScoreRangeRows;
import com.enjoyf.platform.page.ScoreSort;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST controller for managing AppUserMessage.
 */
@RestController
@RequestMapping("/api/app-messages")
public class AppUserMessageResource {

    private final Logger log = LoggerFactory.getLogger(AppUserMessageResource.class);

    private static final String ENTITY_NAME = "appMessage";

    private final AppUserMessageService appUserMessageService;

    public AppUserMessageResource(AppUserMessageService appUserMessageService) {
        this.appUserMessageService = appUserMessageService;
    }


    @GetMapping("/my")
    @Timed
    @ApiOperation(value = "我的消息列表", response = ScoreRangeRows.class)
    public ResponseEntity<ScoreRangeRows<AppUserMessageDTO>> getByAppkeyAndUid(
        @RequestParam(defaultValue = "10") Integer psize,
        @RequestParam(defaultValue = "-1") Double flag) {
        String appkey = CommonContextHolder.getContext().getCommonParams().getAppkey();

        if (appkey == null) {
            throw new BusinessException("param.empty", "appkey");
        }

        Long uid = EnjoySecurityUtils.getCurrentUid();

        ScoreRange scoreRange = new ScoreRange(-1, flag, psize, ScoreSort.DESC);
        ScoreRangeRows<AppUserMessageDTO> rangeRows = appUserMessageService.findByUidAppkey(uid, appkey, scoreRange);

        return ResponseEntity.ok(rangeRows);
    }


    /**
     * GET  /app-messages/:id : get the "id" appMessage.
     *
     * @param id the id of the appMessage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the appMessage, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    @ApiOperation(value = "根据ID获取我的消息", response = ScoreRangeRows.class)
    public ResponseEntity<AppUserMessage> getAppMessage(@PathVariable Long id) {
        log.debug("REST request to get AppUserMessage : {}", id);
        AppUserMessage appUserMessage = appUserMessageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(appUserMessage));
    }

    /**
     * DELETE  /app-messages/:id : delete the "id" appMessage.
     *
     * @param id the id of the appMessage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @Timed
    @ApiOperation(value = "删除我的消息", response = String.class)
    public ResponseEntity<String> deleteAppMessage(@PathVariable Long id) {
        log.debug("REST request to delete AppUserMessage : {}", id);

        long uid = EnjoySecurityUtils.getCurrentUid();
        boolean result = appUserMessageService.delete(id, uid);
        return ResponseEntity.ok().
            headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
            .body("success");
    }


    @GetMapping("/unread/sum")
    @Timed
    @ApiOperation(value = "获取未读用户消息数", response = AppUserMessageSum.class)
    public ResponseEntity<AppUserMessageSum> getSumByAppkeyAndId() {
        log.debug("REST request to delete AppUserMessage : {}");

        Long id = EnjoySecurityUtils.getCurrentUid();
        String appkey = CommonContextHolder.getContext().getCommonParams().getAppkey();

        if (appkey == null || id == null) {
            throw new BusinessException("param.empty", "appkey");
        }

        AppUserMessageSum sum = appUserMessageService.getAppMessageSum(appkey, id);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sum));
    }

    @PutMapping("/readmessage/{id}")
    @Timed
    @ApiOperation(value = "读取未读消息", response = String.class)
    public ResponseEntity<String> readMessage(@PathVariable Long id) {
        log.debug("REST request to delete AppUserMessage : {}", id);

        Long uid = EnjoySecurityUtils.getCurrentUid();

        boolean result = appUserMessageService.readAppMessage(uid, id);

        String resultBody = result ? "success" : "failed";
        return ResponseEntity.ok().
            headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, "read:" + id.toString())).body(resultBody);
    }
}
