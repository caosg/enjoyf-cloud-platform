package com.enjoyf.platform.userservice.web.rest.v2;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.autoconfigure.security.EnjoySecurityUtils;
import com.enjoyf.platform.autoconfigure.security.jwt.JWTConfigurer;
import com.enjoyf.platform.autoconfigure.security.jwt.TokenProvider;
import com.enjoyf.platform.autoconfigure.web.error.CustomParameterizedException;
import com.enjoyf.platform.userservice.config.Constants;
import com.enjoyf.platform.userservice.domain.UserAccount;
import com.enjoyf.platform.userservice.domain.UserLogin;
import com.enjoyf.platform.userservice.service.UserAccountService;
import com.enjoyf.platform.userservice.service.dto.AccountDTO;
import com.enjoyf.platform.userservice.service.dto.BindDTO;
import com.enjoyf.platform.userservice.service.dto.RegisterReqDTO;
import com.enjoyf.platform.userservice.service.dto.SocialAuthDTO;
import com.enjoyf.platform.userservice.service.exception.MobileException;
import com.enjoyf.platform.userservice.service.exception.UserException;
import com.enjoyf.platform.userservice.web.rest.util.HeaderUtil;
import com.enjoyf.platform.userservice.web.rest.vm.LoginVM;
import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by shuguangcao on 2017/7/7.
 */
@RestController
@RequestMapping("/api")
public class UserResource {
    private final Logger log = LoggerFactory.getLogger(UserResource.class);
    private static final String ENTITY_NAME = "userAccount";

    private final UserAccountService userAccountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private JHipsterProperties jHipsterProperties;

    private long tokenValiditySeconds;

    private long tokenValiditySecondsForRememberMe;

    public UserResource(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }


    @PostConstruct
    public void init() {
        this.tokenValiditySeconds = this.jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
        this.tokenValiditySecondsForRememberMe =
            this.jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSecondsForRememberMe();
    }


    /**
     * 用户注册
     *
     * @param registerReqDTO
     * @return
     */
    @PostMapping("/users/register")
    @ApiOperation(value = "用户注册", response = AccountDTO.class)
    @Timed
    public ResponseEntity<AccountDTO> register(@Valid @RequestBody RegisterReqDTO registerReqDTO, HttpServletResponse response) {
        AccountDTO accountDTO;
        try {
            accountDTO = userAccountService.register(registerReqDTO);
        } catch (UserException e) {
            log.info("register errror:" + e.getMessage());
            throw new CustomParameterizedException(e.getMessage(), registerReqDTO.toString());
        }
        this.authenticate(registerReqDTO.getLogin() + "," + registerReqDTO.getLoginDomain(), registerReqDTO.getPassword());
        this.createToken(accountDTO.getUserProfile().getId(), accountDTO.getUserProfile().getProfileNo(), registerReqDTO.getLoginDomain(), false, response);
        accountDTO.setTokenValiditySeconds(this.tokenValiditySeconds);
        return ResponseEntity.ok(accountDTO);


    }

    /**
     * 登陆
     *
     * @param loginVM
     * @param response
     * @return
     */
    @PostMapping("/users/login")
    @Timed
    @ApiOperation(value = "用户登陆", response = AccountDTO.class)
    public ResponseEntity<AccountDTO> login(@Valid @RequestBody LoginVM loginVM, HttpServletResponse response) {
        log.info("\n ---------- login info:-------------\n {} \n --------------------------------", loginVM);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        if (this.authenticate(loginVM.getUsername() + "," + loginVM.getLoginDomain(), loginVM.getPassword())) {
            AccountDTO accountDTO = this.userAccountService.getAccountByLogin(loginVM.getUsername(), loginVM.getLoginDomain(), rememberMe, loginVM.getProfileKey());
            this.createToken(accountDTO.getUserProfile().getId(), accountDTO.getUserProfile().getProfileNo(), loginVM.getLoginDomain(), rememberMe, response);
            return ResponseEntity.ok(accountDTO);
        } else
            return new ResponseEntity<>(null,
                HeaderUtil.createFailureAlert(ENTITY_NAME, "login", "login is fail"), HttpStatus.UNAUTHORIZED);
    }

    private boolean authenticate(String login, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(login, password);
        try {
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException ae) {
            log.info("Authentication exception trace: {}", ae);
            return false;
        }
        return true;
    }

    private void createToken(Long uid, String profileNo, String loginDomain, boolean rememberMe, HttpServletResponse response) {
        Map<String, Object> loginInfo = new HashMap<>();
        loginInfo.put(EnjoySecurityUtils.PROFILE_NO, profileNo);
        loginInfo.put(EnjoySecurityUtils.LOGIN_DOMAIN, StringUtils.isEmpty(loginDomain) ? "mobile" : loginDomain);
        loginInfo.put(EnjoySecurityUtils.UID, uid.toString());
        String jwt = tokenProvider.createToken(loginInfo, SecurityContextHolder.getContext().getAuthentication(), rememberMe);
        // Http header 添加 Authorization ：Bearer {token} ，*重要*
        response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
    }


    /**
     * GET  /accounts/:id : get the "id" userAccount.
     *
     * @param id the id of the userAccount to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userAccount, or with status 404 (Not Found)
     */
    @GetMapping("/users/{id}")
    @Timed
    @ApiOperation(value = "通过Id获得账户信息", response = UserAccount.class)
    public ResponseEntity<UserAccount> getUserAccount(@PathVariable Long id) {
        log.debug("REST request to get UserAccount : {}", id);
        UserAccount userAccount = userAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userAccount));
    }



    /**
     * 获得当前登陆用户账号信息
     *
     * @return
     */
    @GetMapping("/users/current")
    @Timed
    @ApiOperation(value = "获得当前登陆用户信息", notes = "Header需要token", response = AccountDTO.class)
    public ResponseEntity<AccountDTO> getCurrentAccount() {
        AccountDTO accountDTO = userAccountService.getCurrentAccount();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(accountDTO));
    }


    /**
     * qq、微博等第三方登陆
     *
     * @param socialAuthDTO
     * @return
     */
    @PostMapping("/users/auth")
    @Timed
    @ApiOperation(value = "qq、微博等第三方登陆", response = AccountDTO.class)
    public ResponseEntity<AccountDTO> socialLogin(@Valid @RequestBody SocialAuthDTO socialAuthDTO, HttpServletResponse response) {
        AccountDTO accountDTO = userAccountService.auth(socialAuthDTO);
        if (this.authenticate(socialAuthDTO.getLogin() + "," + socialAuthDTO.getLoginDomain(), socialAuthDTO.getPassword())) {
            this.createToken(accountDTO.getUserProfile().getId(), accountDTO.getUserProfile().getProfileNo(), socialAuthDTO.getLoginDomain(), false, response);
            return ResponseEntity.ok(accountDTO);
        } else
            return new ResponseEntity<>(null, HeaderUtil.createFailureAlert(ENTITY_NAME, "auth", "第三方登陆失败"), HttpStatus.UNAUTHORIZED);
    }


    /**
     * 绑定操作
     *
     * @param bindDTO
     * @return
     */
    @PutMapping("/users/bind")
    @Timed
    @ApiOperation(value = "绑定第三方账号", response = AccountDTO.class)
    public ResponseEntity<AccountDTO> bind(@Valid @RequestBody BindDTO bindDTO) {
        log.info("bind third account to joyme. bind info:{}", bindDTO);
        AccountDTO accountDTO = null;
        try {
            accountDTO = userAccountService.bind(bindDTO);
        } catch (UserException e) {
            log.info("bind error:{}", e.getMessage());
            throw new CustomParameterizedException(e.getMessage());
        }
        return ResponseEntity.ok(accountDTO);
    }

    /**
     * 解除绑定
     *
     * @param accountNo
     * @param domain
     * @param profileKey
     * @return
     */
    @PutMapping("/users/unbind")
    @Timed
    @ApiOperation(value = "解绑第三方账号", response = Boolean.class)
    public ResponseEntity<Boolean> unBind(@RequestParam String accountNo, @RequestParam String domain, @RequestParam String profileKey) {
        boolean result = userAccountService.unBind(accountNo, domain, profileKey);
        return ResponseEntity.ok(result);
    }


    @PutMapping("/users/change_password")
    @Timed
    @ApiOperation(value = "修改密码", notes = "需要登陆后操作,请求Header必须有合法token")
    public ResponseEntity changePassword(@RequestParam @NotNull String password) {
        if (!checkPasswordLength(password)) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        userAccountService.changePassword(password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/users/reset-password")
    @Timed
    @ApiOperation(value = "忘记密码")
    public ResponseEntity forgetPassword(@RequestParam String mobileNo, @RequestParam String validCode,
                                         @RequestParam @NotNull String password) {
        if (!checkPasswordLength(password)) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        try {
            userAccountService.forgetPassword(mobileNo, validCode, password);
        } catch (MobileException e) {
            throw new CustomParameterizedException(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= 6 &&
            password.length() <= 40;
    }

    @PutMapping("/users/change_mobile")
    @Timed
    @ApiOperation(value = "修改手机号", notes = "需要登陆后操作,请求Header必须有合法token")
    public ResponseEntity<UserLogin> changeMobileNo(@RequestParam @Valid @Pattern(regexp = Constants.MOBILE_REGEX) String newMobileNo) {
        UserLogin login;
        try {
            login = userAccountService.changeMobileNo(newMobileNo);
        } catch (UserException e) {
            throw new CustomParameterizedException(e.getMessage(), newMobileNo);
        }
        return ResponseEntity.ok(login);
    }
}
