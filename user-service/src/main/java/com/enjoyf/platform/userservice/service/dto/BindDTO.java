package com.enjoyf.platform.userservice.service.dto;

import com.enjoyf.platform.userservice.service.util.UserSeviceUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by shuguangcao on 2017/3/22.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class BindDTO  implements Serializable{
    private String login , password="123456",accountNo, nick ,icon, loginDomain,profileKey,createdIp,appKey;
    private String profileNo;
    private Map<String,String> extraParams;

    public BindDTO() {
    }

    public BindDTO(String login, String password, String accountNo, String nick, String icon, String loginDomain, String profileKey, String createdIp, String appKey, Map<String, String> extraParams) {
        this.login = login;
        this.password = password;
        this.accountNo = accountNo;
        this.nick = nick;
        this.icon = icon;
        this.loginDomain = loginDomain;
        this.profileKey = profileKey;
        this.createdIp = createdIp;
        this.appKey = appKey;
        this.extraParams = extraParams;
    }
    @NotNull
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    @NotNull
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @NotNull
    public String getLoginDomain() {
        return loginDomain;
    }

    public void setLoginDomain(String loginDomain) {
        this.loginDomain = loginDomain;
    }

    public String getProfileKey() {
        return profileKey;
    }

    public void setProfileKey(String profileKey) {
        this.profileKey = profileKey;
    }

    public String getCreatedIp() {
        return createdIp;
    }

    public void setCreatedIp(String createdIp) {
        this.createdIp = createdIp;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getProfileNo() {
        this.profileNo = StringUtils.isEmpty(this.profileNo)?UserSeviceUtil.generateProfileNo(this.getAccountNo(), this.getProfileKey()):this.profileNo;
        return this.profileNo;
    }

    public void setProfileNo(String profileNo) {
        this.profileNo = profileNo;
    }

    public Map<String, String> getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(Map<String, String> extraParams) {
        this.extraParams = extraParams;
    }

    @Override
    public String toString() {
        return "BindDTO{" +
            "login='" + login + '\'' +
            ", password='" + password + '\'' +
            ", accountNo='" + accountNo + '\'' +
            ", nick='" + nick + '\'' +
            ", icon='" + icon + '\'' +
            ", loginDomain='" + loginDomain + '\'' +
            ", profileKey='" + profileKey + '\'' +
            ", createdIp='" + createdIp + '\'' +
            ", appKey='" + appKey + '\'' +
            ", extraParams=" + extraParams +
            '}';
    }
}
