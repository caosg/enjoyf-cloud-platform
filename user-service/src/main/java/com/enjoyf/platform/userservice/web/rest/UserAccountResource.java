package com.enjoyf.platform.userservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.autoconfigure.web.error.CustomParameterizedException;
import com.enjoyf.platform.userservice.config.Constants;
import com.enjoyf.platform.userservice.domain.UserAccount;
import com.enjoyf.platform.userservice.domain.UserLogin;
import com.enjoyf.platform.autoconfigure.security.EnjoySecurityUtils;
import com.enjoyf.platform.autoconfigure.security.jwt.JWTConfigurer;
import com.enjoyf.platform.autoconfigure.security.jwt.TokenProvider;
import com.enjoyf.platform.userservice.service.UserAccountService;
import com.enjoyf.platform.userservice.service.dto.AccountDTO;
import com.enjoyf.platform.userservice.service.dto.BindDTO;
import com.enjoyf.platform.userservice.service.dto.RegisterReqDTO;
import com.enjoyf.platform.userservice.service.dto.SocialAuthDTO;
import com.enjoyf.platform.userservice.service.exception.MobileException;
import com.enjoyf.platform.userservice.service.exception.UserException;
import com.enjoyf.platform.userservice.web.rest.util.HeaderUtil;
import com.enjoyf.platform.userservice.web.rest.util.PaginationUtil;
import com.enjoyf.platform.userservice.web.rest.vm.LoginVM;
import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import java.net.URISyntaxException;
import java.util.*;

/**
 * REST controller for managing UserAccount.
 */
@RestController
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(value = "用户账号API")
public class UserAccountResource {

    private final Logger log = LoggerFactory.getLogger(UserAccountResource.class);

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

    public UserAccountResource(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }


    @PostConstruct
    public void init() {
        this.tokenValiditySeconds = this.jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
        this.tokenValiditySecondsForRememberMe =
            this.jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSecondsForRememberMe();
    }


//    /**
//     * POST  /accounts : Create a new userAccount.
//     *
//     * @param userAccount the userAccount to create
//     * @return the ResponseEntity with status 201 (Created) and with body the new userAccount, or with status 400 (Bad Request) if the userAccount has already an ID
//     * @throws URISyntaxException if the Location URI syntax is incorrect
//     */
//    @PostMapping("/accounts")
//    @Timed
//    @ApiOperation(value = "Create a new userAccount",
//        notes = "id 不需要赋值",
//        response = UserAccount.class,
//        responseContainer = "ResponseEntity")
//    public ResponseEntity<UserAccount> createUserAccount(@Valid @RequestBody UserAccount userAccount) throws URISyntaxException {
//        log.debug("REST request to save UserAccount : {}", userAccount);
//        if (userAccount.getId() != null) {
//            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userAccount cannot already have an ID")).body(null);
//        }
//        UserAccount result = userAccountService.save(userAccount);
//        return ResponseEntity.created(new URI("/api/accounts/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
//            .body(result);
//    }

    /**
     * 用户注册
     *
     * @param registerReqDTO
     * @return
     */
    @PostMapping("/register")
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
    @PostMapping("/login")
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
     * PUT  /accounts : Updates an existing userAccount.
     *
     * @param userAccount the userAccount to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userAccount,
     * or with status 400 (Bad Request) if the userAccount is not valid,
     * or with status 500 (Internal Server Error) if the userAccount couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/accounts")
    @Timed
    @ApiOperation(value = "更新账号", response = UserAccount.class)
    public ResponseEntity<UserAccount> updateUserAccount(@Valid @RequestBody UserAccount userAccount) throws URISyntaxException {
        log.debug("REST request to update UserAccount : {}", userAccount);
        if (userAccount.getId() == null) {
            throw new CustomParameterizedException("Id must not is null", userAccount.toString());
        }
        UserAccount result = userAccountService.save(userAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userAccount.getId().toString()))
            .body(result);
    }


    /**
     * GET  /accounts : get all the userAccounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userAccounts in body
     */
    @GetMapping("/accounts")
    @Timed
    @ApiOperation(value = "获取所有账号", response = UserAccount.class, responseContainer = "List")
    public ResponseEntity<List<UserAccount>> getAllUserAccounts(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of UserAccounts");
        Page<UserAccount> page = userAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/accounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /accounts/:id : get the "id" userAccount.
     *
     * @param id the id of the userAccount to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userAccount, or with status 404 (Not Found)
     */
    @GetMapping("/accounts/{id}")
    @Timed
    @ApiOperation(value = "通过Id获得账户信息", response = UserAccount.class)
    public ResponseEntity<UserAccount> getUserAccount(@PathVariable Long id) {
        log.debug("REST request to get UserAccount : {}", id);
        UserAccount userAccount = userAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userAccount));
    }


//    /**
//     * DELETE  /accounts/:id : delete the "id" userAccount.
//     *
//     * @param id the id of the userAccount to delete
//     * @return the ResponseEntity with status 200 (OK)
//     */
//    @DeleteMapping("/accounts/{id}")
//    @Timed
//    public ResponseEntity<Void> deleteUserAccount(@PathVariable Long id) {
//        log.debug("REST request to delete UserAccount : {}", id);
//        userAccountService.delete(id);
//        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
//    }

    /**
     * 获得当前登陆用户账号信息
     *
     * @return
     */
    @GetMapping("/accounts/current")
    @Timed
    @ApiOperation(value = "获得当前登陆用户信息", notes = "Header需要token", response = AccountDTO.class)
    public ResponseEntity<AccountDTO> getCurrentAccount() {
        AccountDTO accountDTO = userAccountService.getCurrentAccount();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(accountDTO));
    }

    /**
     * 获得用户账号信息
     *
     * @param accountNo
     * @param profileKey
     * @return
     */
    @GetMapping("/accounts/by")
    @Timed
    @ApiOperation(value = "通过账号、profileKey查询账户信息", response = AccountDTO.class)
    public ResponseEntity<AccountDTO> getAccountByAccountNoKey(@RequestParam String accountNo, @RequestParam String profileKey) {
        AccountDTO accountDTO = userAccountService.getAccountByAccountNo(accountNo, profileKey);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(accountDTO));
    }

    /**
     * 通过 profileNo 查询用户账户信息
     *
     * @param profileNo
     * @return
     */
    @GetMapping("/accounts/profile-no/{profileNo}")
    @Timed
    @ApiOperation(value = "通过profileNo查询用户账户信息", response = AccountDTO.class)
    public ResponseEntity<AccountDTO> getAccountByProfileNo(@ApiParam(value = "用户profle id") @PathVariable String profileNo) {
        AccountDTO accountDTO = userAccountService.getAccountByProfileNo(profileNo);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(accountDTO));
    }

    /**
     * 通过 profile id 查询用户账户信息
     *
     * @param profileId
     * @return
     */
    @GetMapping("/accounts/profile-id/{profileId}")
    @Timed
    @ApiOperation(value = "通过profileid查询用户账户信息", response = AccountDTO.class)
    public ResponseEntity<AccountDTO> getAccountByProfileId(@PathVariable Long profileId, HttpServletResponse response) {
        AccountDTO accountDTO = userAccountService.getAccountByProfileId(profileId);
        this.createToken(accountDTO.getUserProfile().getId(), accountDTO.getUserProfile().getProfileNo(), "mobile", false, response);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(accountDTO));
    }

    /**
     * qq、微博等第三方登陆
     *
     * @param socialAuthDTO
     * @return
     */
    @PostMapping("/accounts/auth")
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
    @PutMapping("/accounts/bind")
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
    @PutMapping("/accounts/unbind")
    @Timed
    @ApiOperation(value = "解绑第三方账号", response = Boolean.class)
    public ResponseEntity<Boolean> unBind(@RequestParam String accountNo, @RequestParam String domain, @RequestParam String profileKey) {
        boolean result = userAccountService.unBind(accountNo, domain, profileKey);
        return ResponseEntity.ok(result);
    }

    /**
     * 通过账号和登陆方式查询登陆信息
     *
     * @param accountNo
     * @param domains
     * @return
     */
    @GetMapping("/accounts/logins/{accountNo}")
    @Timed
    @ApiOperation(value = "通过账号和登陆方式查询登陆信息", response = UserLogin.class, responseContainer = "List")
    public ResponseEntity<List<UserLogin>> getUserLoginsByAccountNoAndLogindomains(@PathVariable String accountNo,
                                                                                   @RequestParam Set<String> domains) {

        return new ResponseEntity<>(userAccountService.findUserLoginsByAccountNoAndLogindomains(accountNo, domains), HttpStatus.OK);
    }

    @PutMapping("/accounts/change_password")
    @Timed
    @ApiOperation(value = "修改密码", notes = "需要登陆后操作,请求Header必须有合法token")
    public ResponseEntity changePassword(@RequestParam @NotNull String password) {
        if (!checkPasswordLength(password)) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        userAccountService.changePassword(password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/accounts/forget-password")
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

    @PutMapping("/accounts/change_mobile")
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
