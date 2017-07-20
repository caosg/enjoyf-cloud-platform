package com.enjoyf.platform.userservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.userservice.service.UserAccountService;
import com.enjoyf.platform.userservice.service.dto.AccountDTO;
import com.enjoyf.platform.userservice.service.exception.SendSMSOutLimitException;
import com.enjoyf.platform.autoconfigure.web.error.CustomParameterizedException;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing UserMobile.
 */
@RestController
@RequestMapping("/api")
@Api(value = "手机短信服务")
public class UserMobileResource {

    private final Logger log = LoggerFactory.getLogger(UserMobileResource.class);

    private static final String ENTITY_NAME = "userMobile";

    private final UserAccountService userAccountService;

    public UserMobileResource(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;

    }

    /**
     * 手机是否存在
     *
     * @param mobile
     * @param profilekey
     * @return
     */
    @GetMapping("/user-mobiles/{mobile}/exists")
    @Timed
    public ResponseEntity<Boolean> mobileExists(@PathVariable String mobile, @RequestParam String profilekey) {
        return ResponseEntity.ok(userAccountService.getUserMobileByMobileAndProfileKey(mobile, profilekey) != null);
    }

    /**
     * 注册时发送手机验证码
     *
     * @param mobile
     * @return
     */
    @PutMapping("/user-mobiles/{mobile}/register/code")
    @Timed
    public ResponseEntity<Boolean> sendRegisterCode(@PathVariable String mobile, @RequestParam String profilekey) {
//        //验证手机验证码
//        if (userAccountService.getUserMobileByMobileAndProfileKey(mobile, profilekey) != null) {
//            throw new CustomParameterizedException("mobile.has.exists");
//        }
        try {
            userAccountService.sendMobileCodeByMobile(mobile);
        }catch (SendSMSOutLimitException ex){
            throw new CustomParameterizedException(ex.getMessage());
        }
        return ResponseEntity.ok(true);
    }

    /**
     * 纯粹的发送手机验证码，与业务无关
     * @param mobile
     * @return
     */
    @PutMapping("/user-mobiles/{mobile}/valid")
    @Timed
    public ResponseEntity<Boolean> sendValidCode(@PathVariable String mobile) {
        try {
            userAccountService.sendMobileCodeByMobile(mobile);
        }catch (SendSMSOutLimitException ex){
            throw new CustomParameterizedException(ex.getMessage());
        }
        return ResponseEntity.ok(true);
    }
    /**
     * 注册时候手机验证
     *
     * @param mobile
     * @param code
     * @return
     */
    @GetMapping("/user-mobiles/{mobile}/register/verfiy")
    @Timed
    public ResponseEntity<Boolean> verifyRegisterBindCode(@PathVariable String mobile, @RequestParam String code) {
        //验证手机验证码
        boolean verify = userAccountService.verifyMobileCodeByMobile(mobile, code);
        if (!verify) {
            throw new CustomParameterizedException("mobile.verify.failed");
        }

        return ResponseEntity.ok(true);
    }

    /**
     * 用于用户登录后发送手机验证码
     *
     * @param mobile
     * @return
     */
    @PutMapping("/user-mobiles/{mobile}/code")
    @Timed
    public ResponseEntity<Boolean> sendBindCode(@PathVariable String mobile) {
        AccountDTO accountDTO = userAccountService.getCurrentAccount();
        try {
            userAccountService.sendMobileCodeByProfile(mobile, accountDTO.getUserProfile().getProfileNo());
        }catch (SendSMSOutLimitException ex){
            throw new CustomParameterizedException(ex.getMessage());
        }
        return ResponseEntity.ok(true);
    }


    /**
     * 登陆后手机验证码验证
     *
     * @param mobile
     * @param code
     * @return
     */
    @PostMapping("/user-mobiles/{mobile}/verfiy")
    @Timed
    public ResponseEntity<Boolean> verifyBindCode(@PathVariable String mobile, @RequestParam String code) {
        AccountDTO accountDTO = userAccountService.getCurrentAccount();
//        //用户已经绑定了手机
//        if (userAccountService.profileHasBinedMobile(accountDTO.getUserProfile())) {
//            throw new CustomParameterizedException("user.has.binded.mobile");
//        }
//
//        //手机已经被绑定
//        if (userAccountService.getUserMobileByMobileAndProfileKey(mobile, accountDTO.getUserProfile().getProfileKey()) != null) {
//            throw new CustomParameterizedException("mobile.has.binded");
//        }

        //验证手机验证码
        boolean verify = userAccountService.verifyMobileCodeByMobile(mobile, accountDTO.getUserProfile().getProfileNo(), code);
        if (!verify) {
            throw new CustomParameterizedException("mobile.verify.failed");
        }

        return ResponseEntity.ok(verify);
    }

}
