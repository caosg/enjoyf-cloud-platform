package com.enjoyf.platform.userservice.service;

import com.enjoyf.platform.userservice.domain.UserAccount;
import com.enjoyf.platform.userservice.domain.UserLogin;
import com.enjoyf.platform.userservice.domain.UserMobile;
import com.enjoyf.platform.userservice.domain.UserProfile;
import com.enjoyf.platform.userservice.domain.redis.UserProfileCode;
import com.enjoyf.platform.userservice.service.dto.BindDTO;
import com.enjoyf.platform.userservice.service.dto.RegisterReqDTO;
import com.enjoyf.platform.userservice.service.dto.AccountDTO;
import com.enjoyf.platform.userservice.service.dto.SocialAuthDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service Interface for managing UserAccount.
 */
public interface UserAccountService {

    /**
     * 账户注册
     * @param registerReqDTO
     * @return registeryResDTO
     */
    AccountDTO register(RegisterReqDTO registerReqDTO);

    /**
     * 当前登陆用户的账号信息
     * @return
     */
    AccountDTO getCurrentAccount();

    /**
     * 登陆时获得用户账户信息
     * @param login
     * @param rememberMe
     * @param profileKey
     * @return
     */
    AccountDTO getAccountByLogin(String login,String loginDomain,boolean rememberMe,String profileKey);

    /**
     * 根据账号查询用户账户信息
     * @param accountNo
     * @param profileKey
     * @return
     */
    AccountDTO getAccountByAccountNo(String accountNo, String profileKey);

    /**
     * 根据账号获得账号信息
     * @param accountNo
     * @return
     */
    UserAccount getUserAccount(String accountNo);

    /**
     * 通过登陆账号查询登陆信息
     * @param login 登陆账号
     * @return
     */
    Optional<UserLogin> findUserLoginByLogin(String login);

    /**
     * 通过账号和登陆方式查询登陆信息
     * @param accountNo
     * @param domains
     * @return
     */
    List<UserLogin>  findUserLoginsByAccountNoAndLogindomains(String accountNo, Set<String> domains);

    /**
     * qq、微博第三方登陆
     * @param socialAuthDTO
     * @return
     */
    AccountDTO auth(SocialAuthDTO socialAuthDTO);

    /**
     * 通过 userProfile Id 获得用户账户信息
     * @param profileId the Id of the UserProfile
     * @return
     */
    AccountDTO getAccountByProfileId(Long profileId);

    /**
     * 根据 userProfile profileNo 获得用户账户信息
     * @param profileNo
     * @return
     */
    AccountDTO getAccountByProfileNo(String profileNo);

    /**
     * Save a userAccount.
     *
     * @param userAccount the entity to save
     * @return the persisted entity
     */
    UserAccount save(UserAccount userAccount);

    /**
     * update current logins's mobileNo
     * @param newMobileNo
     * @return
     */
    UserLogin changeMobileNo(String newMobileNo);
    /**
     * update current login's password
     * @param password
     */
    void changePassword(String password);

    /**
     * 忘记密码，修改密码
     * @param mobileNo
     * @param validCode
     * @param newPassword
     */
    void forgetPassword(String mobileNo,String validCode,String newPassword);

    /**
     *  Get all the userAccounts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UserAccount> findAll(Pageable pageable);

    /**
     *  Get the "id" userAccount.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UserAccount findOne(Long id);


    /**
     *  Delete the "id" userAccount.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * 绑定
     * @param bindDTO
     * @return
     */
    AccountDTO bind(BindDTO bindDTO);

    /**
     * 解除绑定
     * @param accountNo
     * @param loginDomain
     * @param profileKey
     * @return
     */
    boolean unBind(String accountNo,String loginDomain,String profileKey);


    /////////////////////////////////////////

    /**
     * 检查用户是否绑定了手机
     * @param userProfile
     * @return
     */
    boolean profileHasBinedMobile(UserProfile userProfile);

    UserMobile getUserMobileByMobileAndProfileKey(String mobile,String profileKey);

    /**
     * 发送绑定手机验证码(通过profileno)
     * @param mobile
     * @return
     */
    boolean sendMobileCodeByProfile(String mobile,String profileNo);


    /**
     * 获取用户的手机验证码（兼容老程序）
     * @param profileNo
     * @return
     */
    UserProfileCode findOneUserProfileCode(String profileNo);

    /**
     * 验证手机验证码
     *
     * @param mobile
     * @param profileNo
     * @param code
     * @return
     */
    boolean verifyMobileCodeByMobile(String mobile, String profileNo, String code);

    /**
     * 验证手机验证码(通过手机)
     *
     * @param mobile
     * @return
     */
    boolean sendMobileCodeByMobile(String mobile);

    /**
     * 验证手机验证码(通过手机)
     *
     * @param mobile
     * @param code
     * @return
     */
    boolean verifyMobileCodeByMobile(String mobile, String code);

}
