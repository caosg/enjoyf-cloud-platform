package com.enjoyf.platform.userservice.service.impl;

import com.enjoyf.platform.autoconfigure.context.CommonContextHolder;
import com.enjoyf.platform.autoconfigure.security.EnjoySecurityUtils;
import com.enjoyf.platform.autoconfigure.storage.StorageService;
import com.enjoyf.platform.autoconfigure.web.error.BusinessException;
import com.enjoyf.platform.common.BFLTSMSsender;
import com.enjoyf.platform.common.SendResult;
import com.enjoyf.platform.userservice.config.ApplicationProperties;
import com.enjoyf.platform.userservice.domain.*;
import com.enjoyf.platform.userservice.domain.enumeration.LoginDomain;
import com.enjoyf.platform.userservice.domain.redis.ProfileRating;
import com.enjoyf.platform.userservice.domain.redis.UserProfileCode;
import com.enjoyf.platform.userservice.repository.UserAccountRepository;
import com.enjoyf.platform.userservice.repository.UserLoginRepository;
import com.enjoyf.platform.userservice.repository.UserMobileRepository;
import com.enjoyf.platform.userservice.repository.redis.RedisAccountDTORepository;
import com.enjoyf.platform.userservice.repository.redis.RedisProfileRatingRepository;
import com.enjoyf.platform.userservice.repository.redis.RedisUserAccountRepository;
import com.enjoyf.platform.userservice.repository.redis.RedisUserProfileCodeRepository;
import com.enjoyf.platform.userservice.security.SecurityUtils;
import com.enjoyf.platform.userservice.service.AppConfService;
import com.enjoyf.platform.userservice.service.UserAccountService;
import com.enjoyf.platform.userservice.service.UserProfileService;
import com.enjoyf.platform.userservice.service.dto.AccountDTO;
import com.enjoyf.platform.userservice.service.dto.BindDTO;
import com.enjoyf.platform.userservice.service.dto.RegisterReqDTO;
import com.enjoyf.platform.userservice.service.dto.SocialAuthDTO;
import com.enjoyf.platform.userservice.service.exception.*;
import com.enjoyf.platform.userservice.service.mapper.RegisterMapper;
import com.enjoyf.platform.userservice.service.mapper.SocialMapper;
import com.enjoyf.platform.userservice.service.util.ProfileFlag;
import com.enjoyf.platform.userservice.service.util.UserSeviceUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.servo.util.Strings;
import io.github.jhipster.config.JHipsterProperties;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Service Implementation for managing UserAccount.
 * <p>
 * 登录、注册等账号认证相关的服务
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final Logger log = LoggerFactory.getLogger(UserAccountServiceImpl.class);

    private final UserAccountRepository userAccountRepository;

    private final RedisUserAccountRepository redisUserAccountRepository;

    private final UserLoginRepository userLoginRepository;

    private final UserProfileService userProfileService;

    private final RedisAccountDTORepository redisAccountDTORepository;

    private final ObjectMapper objectMapper;

    private final BFLTSMSsender bfltsmSsender = new BFLTSMSsender();

    private final RedisUserProfileCodeRepository userProfileCodeRepository;

    private final UserMobileRepository userMobileRepository;

    private final RedisProfileRatingRepository redisProfileRatingRepository;

    @Autowired
    private JHipsterProperties jHipsterProperties;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private StorageService storageService;

    @Autowired
    private AppConfService appConfService;

    private long tokenValiditySeconds;

    private long tokenValiditySecondsForRememberMe;

    public UserAccountServiceImpl(UserAccountRepository userAccountRepository, RedisUserAccountRepository redisUserAccountRepository, UserLoginRepository userLoginRepository,
                                  UserProfileService userProfileService, RedisUserProfileCodeRepository userProfileCodeRepository, UserMobileRepository userMobileRepository,
                                  RedisAccountDTORepository redisAccountDTORepository, RedisProfileRatingRepository redisProfileRatingRepository, ObjectMapper objectMapper) {
        this.userAccountRepository = userAccountRepository;
        this.redisUserAccountRepository = redisUserAccountRepository;
        this.userLoginRepository = userLoginRepository;
        this.userProfileService = userProfileService;
        this.userProfileCodeRepository = userProfileCodeRepository;
        this.redisAccountDTORepository = redisAccountDTORepository;
        this.objectMapper = objectMapper;
        this.userMobileRepository = userMobileRepository;
        this.redisProfileRatingRepository = redisProfileRatingRepository;
    }

    @PostConstruct
    public void init() {
        this.tokenValiditySeconds = this.jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
        this.tokenValiditySecondsForRememberMe =
            this.jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSecondsForRememberMe();
        log.info("\n **** user-service config info ******** \n\t {} \n ------------- end ----------",
            applicationProperties);
    }

    /**
     * 账户注册
     *
     * @param registerReqDTO
     * @return registeryResDTO
     */
    @Override
    @Transactional
    public AccountDTO register(RegisterReqDTO registerReqDTO) {
        if (log.isDebugEnabled())
            log.debug("Register account info : {}", registerReqDTO);
        Assert.notNull(registerReqDTO, "registerReqDTO must be not null1");
        //todo 1.1.0 新增根据appkey获得profilekey
        String profileKey = getCurrentProfileKey();
        if(!StringUtils.isEmpty(profileKey)) {
            registerReqDTO.setProfileKey(profileKey);
            registerReqDTO.setAppKey(CommonContextHolder.getContext().getCommonParams().getAppkey());
        }
        profileKeyIsNull(registerReqDTO.getProfileKey());

        userLoginRepository.findOneByLogin(registerReqDTO.getLogin())
            .map(userLogin -> {
                log.info("login:" + registerReqDTO.getLogin() + " was exist!");
                throw new LoginExistException("login.error.exist");
            })
            .orElseGet(() ->
                userProfileService.findOneByNick(registerReqDTO.getNick().toLowerCase())
                    .map(userProfile -> {
                        log.info("nick:" + registerReqDTO.getNick() + " was exist!");
                        throw new NickExistException("nick.error.exist");
                    }));
        LoginDomain loginDomain = LoginDomain.valueOf(registerReqDTO.getLoginDomain().toUpperCase());
        if (!StringUtils.isEmpty(registerReqDTO.getValidCode()) && loginDomain == LoginDomain.MOBILE) {
            //手机验证码检查
            if (!this.verifyMobileCodeByMobile(registerReqDTO.getLogin(), registerReqDTO.getValidCode())) {
                log.info("mobileNo:" + registerReqDTO.getLogin() + " -- valid code:" + registerReqDTO.getValidCode() + " is wrong.");
                throw new MobileException("mobileNo.error.valid.code");
            }
            // 手机号是否已绑定
            userMobileRepository.findOneByMobileAndProfileKey(registerReqDTO.getLogin(), registerReqDTO.getProfileKey())
                .ifPresent(userMobile ->
                {
                    log.info("mobileNo:" + registerReqDTO.getLogin() + " was binded.");
                    throw new MobileException("mobileNo.error.binded");
                });
        }


        String accountNo = UserSeviceUtil.generateAccountNo();

        UserLogin userLogin = userLoginRepository.save(toLogin(registerReqDTO, accountNo));
        UserAccount userAccount = this.save(toAccount(registerReqDTO, accountNo));
        UserProfile userProfile = userProfileService.save(toProfile(registerReqDTO, accountNo));

        if (loginDomain == LoginDomain.MOBILE) {

            userMobileRepository.save(toMobile(accountNo, registerReqDTO.getLogin(), userProfile.getProfileKey(), userProfile.getProfileNo()));
        }

        AccountDTO accountDTO = new AccountDTO(userProfile.getId(), userProfile.getProfileNo(), userLogin, userAccount, userProfile);
        //todo 兼容旧系统
        accountDTO.setTokenValiditySeconds(this.tokenValiditySeconds);

        // 缓存
        redisAccountDTORepository.save(accountDTO);
        return accountDTO;
    }
    private String getCurrentProfileKey(){
        String appKey = CommonContextHolder.getContext().getCommonParams().getAppkey();
        return appConfService.getProfileKeyByAppKey(appKey);

    }
    /**
     * 当前登陆用户的账号信息
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public AccountDTO getCurrentAccount() {
        String currentProfileNo = EnjoySecurityUtils.getCurrentProfileNo();
        log.info("Current login profile id:{}" + currentProfileNo);
        if (!StringUtils.isEmpty(currentProfileNo)) {
            return this.getAccountByProfileNo(currentProfileNo);
        }
        return null;
    }

    /**
     * 通过登陆查询登陆信息
     *
     * @param login 登陆账号
     * @param loginDomain 登陆方式
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public AccountDTO getAccountByLogin(String login,String loginDomain, boolean rememberMe, String profileKey) {
        UserLogin userLogin = userLoginRepository.findOneByLoginAndLoginDomain(login,loginDomain).get();
        UserAccount userAccount = this.getUserAccount(userLogin.getAccountNo());
        String profileNo = UserSeviceUtil.generateProfileNo(userAccount.getAccountNo(), profileKey);
        UserProfile userProfile = userProfileService.findOneByProfileNo(profileNo);
        AccountDTO accountDTO = new AccountDTO(userProfile.getId(), userProfile.getProfileNo(), userLogin, userAccount, userProfile);
        accountDTO.setTokenValiditySeconds(this.tokenValiditySeconds);
        if (rememberMe)
            accountDTO.setTokenValiditySeconds(this.tokenValiditySecondsForRememberMe);
        // 缓存
        redisAccountDTORepository.save(accountDTO);
        return accountDTO;
    }


    /**
     * 根据账号查询用户账户信息
     *
     * @param accountNo
     * @param profileKey
     * @return
     */
    @Override
    public AccountDTO getAccountByAccountNo(String accountNo, String profileKey) {
        String profileNo = UserSeviceUtil.generateProfileNo(accountNo, profileKey);
        AccountDTO accountDTO = null;
        UserAccount userAccount = this.getUserAccount(accountNo);
        if (userAccount != null) {
            UserProfile userProfile = userProfileService.findOneByProfileNo(profileNo);
            accountDTO = new AccountDTO(null, userAccount, userProfile);
        }
        return accountDTO;
    }


    /**
     * 构造 UserLogin
     *
     * @param registerReqDTO
     * @param accountNo
     * @return
     */
    private UserLogin toLogin(RegisterReqDTO registerReqDTO, String accountNo) {
        UserLogin userLogin = RegisterMapper.MAPPER.toUserLogin(registerReqDTO);
        ZonedDateTime now = ZonedDateTime.now();
        userLogin.setCreatedTime(now);
        userLogin.setAccountNo(accountNo);
        userLogin.setActivated(true);
        userLogin.setCreatedIp(registerReqDTO.getCreatedIp());
        userLogin.setPasswordTime(String.valueOf(System.currentTimeMillis()));
        userLogin.setPassword(UserSeviceUtil.hashPassword(registerReqDTO.getPassword(), userLogin.getPasswordTime()));
        return userLogin;
    }

    private UserLogin socialToLogin(SocialAuthDTO socialAuthDTO, String accountNo) {
        UserLogin userLogin = SocialMapper.MAPPER.toUserLogin(socialAuthDTO);
        ZonedDateTime now = ZonedDateTime.now();
        userLogin.setCreatedTime(now);
        userLogin.setAccountNo(accountNo);
        userLogin.setActivated(true);
        userLogin.setCreatedIp(socialAuthDTO.getCreatedIp());
        userLogin.setPasswordTime(String.valueOf(System.currentTimeMillis()));
        userLogin.setPassword(UserSeviceUtil.hashPassword(socialAuthDTO.getPassword(), userLogin.getPasswordTime()));
        return userLogin;
    }

    private UserAccount toAccount(RegisterReqDTO registerReqDTO, String accountNo) {
        UserAccount userAccount = RegisterMapper.MAPPER.toUserAccount(registerReqDTO);
        userAccount.setCreatedTime(ZonedDateTime.now());
        userAccount.setFlag(0);
        userAccount.setAccountNo(accountNo);
        return userAccount;
    }

    private UserAccount socialToAccount(SocialAuthDTO socialAuthDTO, String accountNo) {
        UserAccount userAccount = SocialMapper.MAPPER.toUserAccount(socialAuthDTO);
        userAccount.setCreatedTime(ZonedDateTime.now());
        userAccount.setFlag(0);
        userAccount.setAccountNo(accountNo);
        return userAccount;
    }

    private UserProfile toProfile(RegisterReqDTO registerReqDTO, String accountNo) {
        UserProfile userProfile = RegisterMapper.MAPPER.toUserProfile(registerReqDTO);
        userProfile.setAccountNo(accountNo);
        userProfile.setProfileNo(UserSeviceUtil.generateProfileNo(accountNo, userProfile.getProfileKey()));
        LoginDomain loginDomain = LoginDomain.valueOf(registerReqDTO.getLoginDomain().toUpperCase());
        if (loginDomain == LoginDomain.MOBILE) {
            userProfile.setMobileNo(registerReqDTO.getLogin());

        }
        int flag = ProfileFlag.getFlagByLoginDomain(loginDomain);
        ProfileFlag profileFlag = new ProfileFlag().has(flag);
        if(!StringUtils.isEmpty(registerReqDTO.getNick()))
            profileFlag.has(ProfileFlag.FLAG_NICK_HASCOMPLETE);
        userProfile.setFlag(profileFlag.getValue());
        userProfile.setLowercaseNick(registerReqDTO.getNick().toLowerCase());
        userProfile.setCreatedTime(ZonedDateTime.now());
        if (!CollectionUtils.isEmpty(registerReqDTO.getExtraParams())) {
            String appKey = registerReqDTO.getExtraParams().getOrDefault("_profile_appkey_", "default");
            userProfile.setAppKey(appKey);
        }

        return userProfile;
    }

    private UserProfile socialToProfile(SocialAuthDTO socialAuthDTO, String accountNo) {
        UserProfile userProfile = SocialMapper.MAPPER.toUserProfile(socialAuthDTO);
        userProfile.setAccountNo(accountNo);
        userProfile.setProfileNo(UserSeviceUtil.generateProfileNo(accountNo, userProfile.getProfileKey()));
        LoginDomain loginDomain = LoginDomain.valueOf(socialAuthDTO.getLoginDomain().toUpperCase());
        if (loginDomain == LoginDomain.MOBILE) {
            userProfile.setMobileNo(socialAuthDTO.getLogin());

        }
        int flag = ProfileFlag.getFlagByLoginDomain(loginDomain);
        ProfileFlag profileFlag = new ProfileFlag();
        userProfile.setFlag(profileFlag.has(flag).getValue());
        userProfile.setLowercaseNick(socialAuthDTO.getNick().toLowerCase());
        userProfile.setCreatedTime(ZonedDateTime.now());
        if (!CollectionUtils.isEmpty(socialAuthDTO.getExtraParams())) {
            String appKey = socialAuthDTO.getExtraParams().getOrDefault("_profile_appkey_", "default");
            userProfile.setAppKey(appKey);
        }
        return userProfile;
    }

    private UserMobile toMobile(String accountNo, String mobileNo, String profileKey, String profileNo) {
        UserMobile userMobile = new UserMobile();
        userMobile.setAccountNo(accountNo);
        userMobile.setCreateTime(ZonedDateTime.now());
        userMobile.setMobile(mobileNo);
        userMobile.setProfileKey(profileKey);
        userMobile.setProfileNo(profileNo);
        return userMobile;
    }

    /**
     * 通过登陆账号查询登陆信息
     *
     * @param login 登陆账号
     * @return
     */
    @Override
    public Optional<UserLogin> findUserLoginByLogin(String login) {
        return userLoginRepository.findOneByLogin(login);
    }

    @Override
    public List<UserLogin> findUserLoginsByAccountNoAndLogindomains(String accountNo, Set<String> domains) {
        if (log.isDebugEnabled())
            log.debug("Find userLogins by accountNo:{} and loginDomains:{}", accountNo, domains);
        return userLoginRepository.findAllByAccountNo(accountNo)
            .stream()
            .filter(userLogin -> domains.contains(userLogin.getLoginDomain()))
            .collect(toList());
    }

    /**
     * qq、微博等第三方登陆
     *
     * @param socialAuthDTO
     * @return
     */
    @Override
    @Transactional
    public AccountDTO auth(SocialAuthDTO socialAuthDTO) {
        Assert.notNull(socialAuthDTO, "registerReqDTO must be not null1");
        log.info("Social Register account info : {}", socialAuthDTO);
        Map<String, String> extParams = socialAuthDTO.getExtraParams();
        String accountNo = CollectionUtils.isEmpty(extParams) ?
            UserSeviceUtil.generateAccountNo() : extParams.getOrDefault("uno", UserSeviceUtil.generateAccountNo());

        //todo 1.1.0 新增根据appkey获得profilekey
        String profileKey = getCurrentProfileKey();
        if(!StringUtils.isEmpty(profileKey)) {
            socialAuthDTO.setProfileKey(profileKey);
            socialAuthDTO.setAppKey(CommonContextHolder.getContext().getCommonParams().getAppkey());
        }
        profileKeyIsNull(socialAuthDTO.getProfileKey());

        UserLogin userLogin;
        UserAccount userAccount = this.getUserAccount(accountNo);
        Optional<UserLogin> userLoginOptional = userLoginRepository.findOneByLoginAndLoginDomain(socialAuthDTO.getLogin(), socialAuthDTO.getLoginDomain());
        if (userLoginOptional.isPresent()) {
            log.info(" login:{} and domain:{} was exist.",socialAuthDTO.getLogin(),socialAuthDTO.getLoginDomain());
            userLogin = userLoginOptional.get();
            if (StringUtils.isEmpty(userLogin.getAccountNo())) {
                if (userAccount == null)
                    userAccount = this.save(socialToAccount(socialAuthDTO, accountNo));
                userLogin.accountNo(accountNo).updatedTime(ZonedDateTime.now());
                userLogin = userLoginRepository.save(userLogin);
            } else {
                if (userAccount == null) {
                    accountNo = userLogin.getAccountNo();
                    userAccount = this.getUserAccount(accountNo);
                }
            }
            /**
             * 额外处理兼容老数据
             */
            if (StringUtils.isEmpty(userLogin.getPassword())) {
                userLogin.setPasswordTime(String.valueOf(System.currentTimeMillis()));
                userLogin.setPassword(UserSeviceUtil.hashPassword(UserSeviceUtil.DEFAULT_PASSWORD, userLogin.getPasswordTime()));
                userLogin.updatedTime(ZonedDateTime.now());
                userLoginRepository.save(userLogin);
            }
        } else {
            log.info(" create new social login info:{}",socialAuthDTO);
            userLogin = userLoginRepository.save(socialToLogin(socialAuthDTO, accountNo));
            if (userAccount == null)
                userAccount = this.save(socialToAccount(socialAuthDTO, accountNo));
        }
        String profileNo = UserSeviceUtil.generateProfileNo(userLogin.getAccountNo(), socialAuthDTO.getProfileKey());
        UserProfile userProfile = userProfileService.findOneByProfileNo(profileNo);
        if (userProfile != null) {
            LoginDomain loginDomain = LoginDomain.valueOf(socialAuthDTO.getLoginDomain().toUpperCase());
            int flag = this.profileFlag(socialAuthDTO.getLoginDomain());
            ProfileFlag profileFlag = new ProfileFlag(userProfile.getFlag());
            if (loginDomain != LoginDomain.CLIENT && !profileFlag.hasFlag(flag)) {
                userProfile.setFlag(profileFlag.has(flag).getValue());
                userProfile = userProfileService.save(userProfile);
            }
        } else {
            if (!StringUtils.isEmpty(socialAuthDTO.getNick()))
                userProfileService.findOneByNick(socialAuthDTO.getNick().toLowerCase())
                    .ifPresent(userProfile1 -> {
                        String randomNick = socialAuthDTO.getNick() + RandomUtils.nextInt();
                        socialAuthDTO.setNick(randomNick);

                    });
            // 上传头像
            if(!StringUtils.isEmpty(socialAuthDTO.getIcon()) )
                socialAuthDTO.setIcon(storageService.uploadFromUrl(socialAuthDTO.getIcon()));
            userProfile = userProfileService.save(socialToProfile(socialAuthDTO, accountNo));

        }
        AccountDTO accountDTO = new AccountDTO(userProfile.getId(), userProfile.getProfileNo(), userLogin, userAccount, userProfile);
        //todo 兼容旧系统
        accountDTO.setTokenValiditySeconds(this.tokenValiditySeconds);

        // 缓存
        redisAccountDTORepository.save(accountDTO);
        return accountDTO;
    }

    /**
     * 通过 userProfile Id 获得用户账户信息
     *
     * @param profileId the Id of the UserProfile
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public AccountDTO getAccountByProfileId(Long profileId) {
        if (log.isDebugEnabled())
            log.debug("Get current account by profileId:{}", profileId);
        if (profileId == null) return null;
        AccountDTO accountDTO = redisAccountDTORepository.findOne(profileId);
        if (accountDTO != null) {
            log.info("------- Get current account from redis -----");
            return accountDTO;
        }
        UserProfile userProfile = userProfileService.findOne(profileId);
        if (userProfile != null) {
            UserAccount userAccount = this.getUserAccount(userProfile.getAccountNo());
            return new AccountDTO(null, userAccount, userProfile);
        }
        return null;
    }

    /**
     * 根据 userProfile profileNo 获得用户账户信息
     *
     * @param profileNo
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public AccountDTO getAccountByProfileNo(String profileNo) {
        if (log.isDebugEnabled())
            log.debug("Get current account by profileNo:{}", profileNo);
        if (StringUtils.isEmpty(profileNo)) return null;
        AccountDTO accountDTO = redisAccountDTORepository.findOneByProfileNo(profileNo)
            .map(existAccountDTO -> {
                log.info("------- Get current account from redis -----");
                return existAccountDTO;
            })
            .orElseGet(() -> {
                log.info("------- Get current account from db -----");
                UserProfile userProfile = userProfileService.findOneByProfileNo(profileNo);
                if (userProfile != null) {
                    UserAccount userAccount = this.getUserAccount(userProfile.getAccountNo());
                    AccountDTO accountDTONew = new AccountDTO(null, userAccount, userProfile);
                    accountDTONew.setTokenValiditySeconds(this.tokenValiditySeconds);
                    return accountDTONew;
                } else
                    return null;
            });


        return accountDTO;
    }

    /**
     * Save a userAccount.
     *
     * @param userAccount the entity to save
     * @return the persisted entity
     */
    @Override
    public UserAccount save(UserAccount userAccount) {
        String profileNo = EnjoySecurityUtils.getCurrentProfileNo();
        if (log.isDebugEnabled())
            log.debug("Request to save UserAccount : {},current login profileNo:{}", userAccount, profileNo);

        UserAccount result = userAccountRepository.save(userAccount);
        //刷新缓存
        if (!StringUtils.isEmpty(profileNo)) {
            redisAccountDTORepository.findOneByProfileNo(profileNo)
                .ifPresent(existAccountDTO -> {
                    log.info("********flush current login's account**********");
                    if (existAccountDTO.getUserAccount().getAccountNo().equals(userAccount.getAccountNo())) {
                        existAccountDTO.setUserAccount(result);
                        redisAccountDTORepository.save(existAccountDTO);
                    }
                });
        }
        redisUserAccountRepository.save(result);
        return result;
    }

    /**
     * update logins's mobileNo
     *
     * @param newMobileNo
     * @return
     */
    @Override
    @Transactional
    public UserLogin changeMobileNo(String newMobileNo) {
        AccountDTO accountDTO = this.getCurrentAccount();
        if (log.isDebugEnabled())
            log.debug(" Change MobileNo:{} by login:{}", newMobileNo, accountDTO.getUserLogin().getLogin());
        UserLogin login = userLoginRepository.findOne(accountDTO.getUserLogin().getId());
        if (!login.getLoginDomain().equals(LoginDomain.MOBILE.getCode())) {
            login = userLoginRepository.findOneByAccountNoAndLoginDomain(login.getAccountNo(), LoginDomain.MOBILE.getCode())
                .orElseThrow(() -> new MobileException("手机登陆方式不存在"));

        }
        userMobileRepository.findOneByMobileAndProfileKey(newMobileNo, accountDTO.getUserProfile().getProfileKey())
            .ifPresent(userMobile -> {
                throw new MobileException("手机号已被绑定");
            });
        userMobileRepository.findOneByMobileAndProfileKey(login.getLogin(), accountDTO.getUserProfile().getProfileKey())
            .ifPresent(userMobile -> userMobile.setMobile(newMobileNo));
        UserProfile profile = userProfileService.findOne(accountDTO.getUserProfile().getId());
        profile.setMobileNo(newMobileNo);
        userProfileService.save(profile);
        login.setLogin(newMobileNo);
        //刷新缓存
        accountDTO.setUserLogin(login);
        accountDTO.setUserProfile(profile);
        redisAccountDTORepository.save(accountDTO);
        return login;
    }


    /**
     * update login's password
     *
     * @param password
     */
    @Override
    @Transactional
    public void changePassword(String password) {
        String pwdTime = String.valueOf(System.currentTimeMillis());

        String loginDomain = EnjoySecurityUtils.getCurrentLoginDomain();
        log.info("Update login:{} password to new password:{} ,domain:{}",SecurityUtils.getCurrentUserLogin(), password, loginDomain);
        // 手机登陆直接修改
        if(!StringUtils.isEmpty(loginDomain) &&
            (loginDomain.equals(LoginDomain.MOBILE.getCode())||loginDomain.equals(LoginDomain.EMAIL.getCode()))) {
            userLoginRepository.findOneByLoginAndLoginDomain(SecurityUtils.getCurrentUserLogin(),loginDomain)
                .ifPresent(userLogin -> updatePassword(password, pwdTime, userLogin));
        }else {
          Optional<UserLogin>  userLoginOptional = userLoginRepository.findOneByLoginAndLoginDomain(SecurityUtils.getCurrentUserLogin(),loginDomain);
          userLoginOptional.ifPresent(userLogin -> {
              userLoginRepository.findOneByAccountNoAndLoginDomain(userLogin.getAccountNo(),LoginDomain.MOBILE.getCode()).ifPresent(
                  userLogin1 -> updatePassword(password, pwdTime, userLogin1)
              );
              userLoginRepository.findOneByAccountNoAndLoginDomain(userLogin.getAccountNo(),LoginDomain.EMAIL.getCode()).ifPresent(
                  userLogin2 -> updatePassword(password, pwdTime, userLogin2)
              );
          });
        }
    }

    private void updatePassword(String password, String pwdTime, UserLogin userLogin) {
        userLogin.setPasswordTime(pwdTime);
        userLogin.setPassword(UserSeviceUtil.hashPassword(password, pwdTime));
        userLogin.setUpdatedTime(ZonedDateTime.now());
        userLoginRepository.save(userLogin);
        log.info("Update login:{} password to new password:{},pwdTime:{}", userLogin.getLogin(), password, pwdTime);
    }

    /**
     * 忘记密码时，修改密码
     *
     * @param mobileNo
     * @param validCode
     * @param newPassword
     */
    @Override
    public void forgetPassword(String mobileNo, String validCode, String newPassword) {
        log.info("*** Update mobile:{} newPassword to :{} , by valid code:{} *****", mobileNo, newPassword, validCode);
        if (StringUtils.isEmpty(mobileNo) || StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(validCode))
            throw new MobileException("params.null.error");
        if (!this.verifyMobileCodeByMobile(mobileNo, validCode))
            throw new MobileException("mobile.valid.code.error");
        String pwdTime = String.valueOf(System.currentTimeMillis());
        userLoginRepository.findOneByLoginAndLoginDomain(mobileNo, LoginDomain.MOBILE.getCode()).ifPresent(userLogin -> {
            updatePassword(newPassword, pwdTime, userLogin);
            log.info("*** It's successful to update login:{} password to new password:{},pwdTime:{}", mobileNo, newPassword, pwdTime);
        });
    }


    /**
     * Get all the userAccounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<UserAccount> findAll(Pageable pageable) {
        log.debug("Request to get all UserAccounts");
        Page<UserAccount> result = userAccountRepository.findAll(pageable);
        return result;
    }

    /**
     * Get one userAccount by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public UserAccount findOne(Long id) {
        log.debug("Request to get UserAccount : {}", id);
        UserAccount userAccount = redisUserAccountRepository.findOne(id);
        if (userAccount == null)
            userAccount = userAccountRepository.findOne(id);
        return userAccount;
    }

    @Override
    public UserAccount getUserAccount(String accountNo) {
        if (log.isDebugEnabled())
            log.debug("Get userAccount by accountNo:{}", accountNo);
        UserAccount userAccount = redisUserAccountRepository.findOneByAccountNo(accountNo);
        if (userAccount == null) {
            Optional<UserAccount> userAccountOptional = userAccountRepository.findOneByAccountNo(accountNo);
            if (userAccountOptional.isPresent()) {
                userAccount = userAccountOptional.get();
                redisUserAccountRepository.save(userAccount);
            }
        }
        return userAccount;
    }


    /**
     * Delete the  userAccount by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        if (log.isDebugEnabled())
            log.debug("Request to delete UserAccount : {}", id);
        userAccountRepository.delete(id);
        redisUserAccountRepository.delete(id);
    }

    /**
     * 绑定手机号
     *
     * @param bindDTO
     * @return
     */
    @Override
    @Transactional
    public AccountDTO bind(BindDTO bindDTO) {
        Assert.notNull(bindDTO, "BindDTO must be not null1");
        if (log.isDebugEnabled())
            log.debug(" Bind to userAccount: {}", bindDTO);
        AccountDTO accountDTO = this.getCurrentAccount();
        if (accountDTO == null)
            throw new ProfileNotExistException("not login");
        //todo 新增根据appkey获得profilekey
        String profileKey = getCurrentProfileKey();
        if(!StringUtils.isEmpty(profileKey)) {
            bindDTO.setProfileKey(profileKey);
            bindDTO.setAppKey(CommonContextHolder.getContext().getCommonParams().getAppkey());
        }
        profileKey = StringUtils.isEmpty(bindDTO.getProfileKey()) ? "www" : bindDTO.getProfileKey();
        LoginDomain loginDomain = LoginDomain.valueOf(bindDTO.getLoginDomain().toUpperCase());
        if (loginDomain == LoginDomain.MOBILE)
            userMobileRepository.findOneByMobileAndProfileKey(bindDTO.getLogin(), profileKey)
                .ifPresent(userMobile -> {
                    log.info("mobileNo:{} was binded.", bindDTO.getLogin());
                    throw new MobileException("mobile.has.binded.error");
                });

        UserProfile userProfile = accountDTO.getUserProfile();
        int flag = this.profileFlag(bindDTO.getLoginDomain());
        if (new ProfileFlag(userProfile.getFlag()).hasFlag(flag))
            throw new ProfileBindedException("third.uid.has.binded.error");
        Optional<UserLogin> userLoginOptional = userLoginRepository.findOneByLoginAndLoginDomain(bindDTO.getLogin(), bindDTO.getLoginDomain());
        UserLogin userLogin = null;
        /**
         * userlogin为空第一次用该账号登陆
         * userlogin不为空但是uno为空，改账号以前被绑定过，但是现在是解绑状态
         * userlogin不为空uno不为空，抛出异常，说明改账号已经被别人绑定了
         */
        if (userLoginOptional.isPresent()) {
            userLogin = userLoginOptional.get();
            if (StringUtils.isEmpty(userLogin.getAccountNo())) {
                userLogin.setAccountNo(accountDTO.getUserAccount().getAccountNo());
                userLogin = userLoginRepository.save(userLogin);
            } else {
                log.info("login:{} from login-domain:{} was binded! ", bindDTO.getLogin(), bindDTO.getLoginDomain().toUpperCase());
                throw new ProfileBindedException("third.uid.has.binded.error");
            }
        } else {
            userLogin = toLogin(bindDTO);
            userLogin.setAccountNo(accountDTO.getUserAccount().getAccountNo());
            userLogin = userLoginRepository.save(userLogin);
        }
        ProfileFlag curProfileFlag = new ProfileFlag(userProfile.getFlag());
        /**
         * 只有初次绑定才会修改昵称
         */
        this.setNick(curProfileFlag, bindDTO, userProfile);
        userProfile.setFlag(curProfileFlag.has(flag).getValue());
        /**
         * 处理头像
         */
        this.setIcons(bindDTO, userProfile);
        if (loginDomain == LoginDomain.MOBILE) {
            userProfile.setMobileNo(bindDTO.getLogin());
            userMobileRepository.save(toMobile(accountDTO.getUserAccount().getAccountNo(), bindDTO.getLogin(), profileKey, userProfile.getProfileNo()));
        }
        userProfile = userProfileService.save(userProfile);

        accountDTO.setUserProfile(userProfile);
        accountDTO.setUserLogin(userLogin);
        redisAccountDTORepository.save(accountDTO);
        return accountDTO;
    }


    /**
     * 解除绑定
     *
     * @param accountNo
     * @param loginDomain
     * @param profileKey
     * @return
     */
    @Override
    @Transactional
    public boolean unBind(String accountNo, String loginDomain, String profileKey) {
        int flag = this.profileFlag(loginDomain);
        AccountDTO accountDTO = this.getCurrentAccount();
        if (accountDTO == null)
            throw new ProfileNotExistException("not login");
        Optional<UserLogin> userLoginOptional = userLoginRepository.findOneByAccountNoAndLoginDomain(accountNo, loginDomain);
        if (!userLoginOptional.isPresent())
            return false;
        UserProfile userProfile = accountDTO.getUserProfile();
        ProfileFlag profileFlag = new ProfileFlag(userProfile.getFlag());
        if (!profileFlag.hasFlag(flag))
            return false;
        userProfile.setFlag(profileFlag.reduce(flag).getValue());
        userProfileService.save(userProfile);
        userLoginRepository.setAccountNoById(userLoginOptional.get().getId(), "");
        accountDTO.setUserProfile(userProfile);
        redisAccountDTORepository.save(accountDTO);
        return true;
    }

    @Override
    public boolean profileHasBinedMobile(UserProfile userProfile) {
        if (log.isDebugEnabled()) {
            log.debug(" Bind to profileHasBinedMobile: userProfile：{}", userProfile);
        }

        ProfileFlag profileFlag = new ProfileFlag(userProfile.getFlag());
        return profileFlag.hasFlag(ProfileFlag.FLAG_MOBILE) && !Strings.isNullOrEmpty(userProfile.getMobileNo());
    }

    @Override
    public UserMobile getUserMobileByMobileAndProfileKey(String mobile, String profileKey) {
        if (log.isDebugEnabled()) {
            log.debug(" Bind to getUserMobileByMobileAndProfileKey: mobile：{}，profileKey：{}", mobile, profileKey);
        }

        Optional<UserMobile> userMobile = userMobileRepository.findOneByMobileAndProfileKey(mobile, profileKey);

        return (userMobile != null && userMobile.isPresent()) ? userMobile.get() : null;
    }

    /**
     * 发送手机验证码
     *
     * @param mobile
     * @param profileNo
     * @return
     */
    @Override
    public boolean sendMobileCodeByProfile(String mobile, String profileNo) {
        if (log.isDebugEnabled()) {
            log.debug(" Bind to sendMobileCode: mobile：{}，profileNo：{}", mobile, profileNo);
        }

        Long mobileRating = redisProfileRatingRepository.getMobileRating(profileNo, ProfileRating.RATING_MOBILE);
        if (mobileRating >= applicationProperties.getMobile().getRating()) {
            throw new SendSMSOutLimitException("sendsms.times.outlimit");
        }

        String code = UserSeviceUtil.generateMobileCode();
        // todo
        SendResult result = bfltsmSsender.sendAction(mobile, "验证码：" + code + "，欢迎来到着迷网【着迷网】");
        log.info("mobile:{}-code:{} ,send result:{}", mobile, code,result.getCode());
        redisProfileRatingRepository.incrMobileRating(profileNo, ProfileRating.RATING_MOBILE, 1);

        UserProfileCode userProfileCode = new UserProfileCode();
        userProfileCode.setMobile(mobile);
        userProfileCode.setMobileCode(code);
        userProfileCode.setProfileNo(profileNo);
        userProfileCode.setExpiration(applicationProperties.getMobile().getExpireSeconds());
        userProfileCodeRepository.save(userProfileCode);

        return true;
    }

    @Override
    public UserProfileCode findOneUserProfileCode(String profileNo) {
        UserProfileCode userProfileCode = userProfileCodeRepository.findOne(profileNo);
        return userProfileCode;
    }

    /**
     * 验证手机验证码
     *
     * @param mobile
     * @param profileNo
     * @param code
     * @return
     */
    @Override
    public boolean verifyMobileCodeByMobile(String mobile, String profileNo, String code) {
        if (code.equals(applicationProperties.getMobile().getTestCode()))
            return true;
        UserProfileCode userProfileCode = userProfileCodeRepository.findOne(profileNo);
        boolean result = userProfileCode != null && mobile.equals(userProfileCode.getMobile()) && code.equals(userProfileCode.getMobileCode());
        if (result)
            userProfileCodeRepository.delete(profileNo);
        return result;
    }

    @Override
    public boolean sendMobileCodeByMobile(String mobile) {
        if (log.isDebugEnabled()) {
            log.debug(" Bind to sendMobileCode: mobile：{}", mobile);
        }

        Long mobileRating = redisProfileRatingRepository.getMobileRating(mobile, ProfileRating.RATING_MOBILE);
        if (mobileRating >= applicationProperties.getMobile().getRating()) {
            throw new SendSMSOutLimitException("sendsms.times.outlimit");
        }

        String code = UserSeviceUtil.generateMobileCode();
        //todo template
        SendResult result = bfltsmSsender.sendAction(mobile, "验证码：" + code + "，欢迎来到着迷网【着迷网】");
        log.info("mobile:{}-code:{},sms send result:{}" ,mobile, code,result.getCode());
        redisProfileRatingRepository.incrMobileRating(mobile, ProfileRating.RATING_MOBILE, 1);

        UserProfileCode userProfileCode = new UserProfileCode();
        userProfileCode.setMobile(mobile);
        userProfileCode.setMobileCode(code);
        userProfileCode.setProfileNo(mobile);
        userProfileCode.setExpiration(applicationProperties.getMobile().getExpireSeconds());
        userProfileCodeRepository.save(userProfileCode);

        return true;
    }

    @Override
    public boolean verifyMobileCodeByMobile(String mobile, String code) {
        if (code.equals(applicationProperties.getMobile().getTestCode()))
            return true;
        UserProfileCode userProfileCode = userProfileCodeRepository.findOne(mobile);
        boolean result = userProfileCode != null && mobile.equals(userProfileCode.getMobile()) && code.equals(userProfileCode.getMobileCode());
        if (result)
            userProfileCodeRepository.delete(mobile);
        return result;
    }


    private int profileFlag(String domain) {
        LoginDomain loginDomain = LoginDomain.valueOf(domain.toUpperCase());
        return ProfileFlag.getFlagByLoginDomain(loginDomain);
    }

    private UserLogin toLogin(BindDTO bindDTO) {
        UserLogin userLogin = new UserLogin();
        userLogin.accountNo(bindDTO.getAccountNo())
            .login(bindDTO.getLogin())
            .loginDomain(bindDTO.getLoginDomain())
            .createdTime(ZonedDateTime.now())
            .passwordTime(System.currentTimeMillis() + "")
            .loginName(bindDTO.getNick());
        if (StringUtils.isEmpty(bindDTO.getPassword())) {
            bindDTO.setPassword("123456");//默认密码
        }
        userLogin.password(UserSeviceUtil.hashPassword(bindDTO.getPassword(), userLogin.getPasswordTime()));
        return userLogin;
    }

    private void setIcons(BindDTO bindDTO, UserProfile userProfile) {
        if (!StringUtils.isEmpty(bindDTO.getIcon())) {
            if (StringUtils.isEmpty(userProfile.getIcons())) {
                List<Icon> icons = new ArrayList<>();
                icons.add(new Icon(0, bindDTO.getIcon()));
                try {
                    userProfile.icons(this.objectMapper.writeValueAsString(icons));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            } else {
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, Icon.class);
                try {
                    List<Icon> icons = this.objectMapper.readValue(userProfile.getIcons(), javaType);
                    icons.add(new Icon(icons.size(), bindDTO.getIcon()));
                    userProfile.setIcons(objectMapper.writeValueAsString(icons));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (StringUtils.isEmpty(userProfile.getIcon()))
                userProfile.setIcon(bindDTO.getIcon());
        }
    }

    private void setNick(ProfileFlag curProfileFlag, BindDTO bindDTO, UserProfile userProfile) {

        boolean nicked = curProfileFlag.equalFlag(ProfileFlag.FLAG_CLIENTID);
        if (nicked && !StringUtils.isEmpty(bindDTO.getNick())) {
            userProfile.nick(bindDTO.getNick()).lowercaseNick(bindDTO.getNick().toLowerCase());
            Optional<UserProfile> sameNickProfileOptional = userProfileService.findOneByNick(bindDTO.getNick().toLowerCase());
            if (sameNickProfileOptional.isPresent()) {
                UserProfile sameNickProfile = sameNickProfileOptional.get();
                if (userProfile.getId() != sameNickProfile.getId()) {
                    userProfile.nick(bindDTO.getNick() + "_" + userProfile.getId())
                        .lowercaseNick(bindDTO.getNick().toLowerCase() + "_" + userProfile.getId());
                }
            }

        }
    }

    private void profileKeyIsNull(String profileKey){
        if(StringUtils.isEmpty(profileKey))
            throw new BusinessException("profileKey 不允许为空");
    }

}
