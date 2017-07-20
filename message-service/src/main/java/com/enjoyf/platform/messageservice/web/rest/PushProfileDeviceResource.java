package com.enjoyf.platform.messageservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.autoconfigure.context.CommonContextHolder;
import com.enjoyf.platform.autoconfigure.security.EnjoySecurityUtils;
import com.enjoyf.platform.autoconfigure.web.error.BusinessException;
import com.enjoyf.platform.common.util.StringUtil;
import com.enjoyf.platform.messageservice.service.PushProfileDeviceService;
import com.enjoyf.platform.messageservice.service.dto.PushProfileDeviceDTO;
import com.enjoyf.platform.messageservice.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.net.URISyntaxException;
import java.util.Locale;

/**
 * REST controller for managing PushProfileDeviceDTO.
 */
@RestController
@RequestMapping("/api/push-profile-device")
public class PushProfileDeviceResource {

    private final Logger log = LoggerFactory.getLogger(PushProfileDeviceResource.class);

    private static final String ENTITY_NAME = "pushProfileDevice";

    private final PushProfileDeviceService pushProfileDeviceService;

    private final MessageSource messageSource;


    public PushProfileDeviceResource(PushProfileDeviceService pushProfileDeviceService, MessageSource messageSource) {
        this.pushProfileDeviceService = pushProfileDeviceService;
        this.messageSource = messageSource;
    }

    /**
     * POST  /push-profile-device-relations : Create a new profileDevice.
     *
     * @param pushProfileDeviceDTO the profileDevice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new profileDevice, or with status 400 (Bad Request) if the profileDevice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    @Timed
    @ApiOperation(value = "保存用户设备关系", response = PushProfileDeviceDTO.class)
    public ResponseEntity<String> createPushProfileDevice(@RequestBody PushProfileDeviceDTO pushProfileDeviceDTO) throws URISyntaxException {
        if (log.isDebugEnabled()) {
            log.debug("REST request to save PushProfileDeviceDTO : {}", pushProfileDeviceDTO);
        }

        String appkey = CommonContextHolder.getContext().getCommonParams().getAppkey();
        if (StringUtil.isEmpty(appkey)) {
            throw new BusinessException(messageSource.getMessage("param.empty", null, Locale.CHINA), "");
        }

        Long uid = EnjoySecurityUtils.getCurrentUid();
        try {
            pushProfileDeviceDTO.setUid(uid);
            pushProfileDeviceDTO.setAppkey(appkey);
            pushProfileDeviceService.savePushProfileDevice(pushProfileDeviceDTO);
        } catch (Exception e) {
            log.error("save pushProfileDevice error.", e);
            throw new BusinessException(messageSource.getMessage("profilepushdevice.save.faild", null, Locale.CHINA), "uid:" + uid);
        }

        return ResponseEntity.ok("success");
    }


    /**
     * DELETE  /push-profile-device/
     *
     * @param
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping
    @Timed
    @ApiOperation(value = "删除用户设备关系", response = String.class)
    public ResponseEntity<String> delPushProfileDevice() {
        log.debug("REST request to delete PushProfileDeviceDTO .");
        Long uid = null;
        String appkey = null;
        try {
            uid = EnjoySecurityUtils.getCurrentUid();
            appkey = CommonContextHolder.getContext().getCommonParams().getAppkey();
            if (StringUtil.isEmpty(appkey) || uid==null) {
                throw new BusinessException(messageSource.getMessage("param.empty", null, Locale.CHINA), "");
            }

            pushProfileDeviceService.deletePushProfileDevice(appkey, uid);
        } catch (Exception e) {
            log.error("save pushProfileDevice error.", e);
            throw new BusinessException(messageSource.getMessage("profilepushdevice.delete.faild", null, Locale.CHINA), "uid:" + uid);

        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, appkey + "@" + uid)).body("success");
    }
}
