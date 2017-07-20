package com.enjoyf.platform.userservice.service.dto;

import com.enjoyf.platform.userservice.service.util.UserSeviceUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;

/**
 * Created by shuguangcao on 2017/5/8.
 */
@ApiModel
public class SocialAuthDTO {

    private String login,nick , validCode,icon, loginDomain,profileKey,createdIp,appKey="default";
    private String password= UserSeviceUtil.DEFAULT_PASSWORD;
    private Map<String,String> extraParams;

    public SocialAuthDTO() {
    }

    public SocialAuthDTO(String login, String password, String nick, String validCode,String icon, String loginDomain, String profileKey, String createdIp, String appKey,
                          Map<String, String> extraParams) {
        this.login = login;
        this.password = password;
        this.nick = nick;
        this.validCode = validCode;
        this.loginDomain = loginDomain;
        this.profileKey = profileKey;
        this.createdIp = createdIp;
        this.appKey = appKey;
        this.icon = icon ;
        this.extraParams = extraParams;
    }
    @NotNull
    @Size(min = 3,max = 64)
    @ApiModelProperty(value = "登陆key",required = true)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    //@NotNull
    public String getPassword() {
        return StringUtils.isEmpty(password)?UserSeviceUtil.DEFAULT_PASSWORD:password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //@NotNull
    //@Size(min=2,max=24)
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @NotNull
    @Size(min = 1,max = 32)
    @ApiModelProperty(value = "登陆方式",allowableValues = "qq,sinaweibo")
    public String getLoginDomain() {
        return loginDomain;
    }

    public void setLoginDomain(String loginDomain) {
        this.loginDomain = loginDomain;
    }

    @NotNull
    @Size(min = 2,max = 16)
    @ApiModelProperty(value = "profileKey",allowableValues = "www")
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Map<String, String> getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(Map<String, String> extraParams) {
        this.extraParams = extraParams;
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

    @Override
    public String toString() {
        return "SocialAuthDTO{" +
            "login='" + login + '\'' +
            ", password='" + password + '\'' +
            ", nick='" + nick + '\'' +
            ", loginDomain='" + loginDomain + '\'' +
            ", profileKey='" + profileKey + '\'' +
            ", createdIp='" + createdIp + '\'' +
            ", appKey='" + appKey + '\'' +
            ", icon='" + icon + '\'' +
            ", extraParams=" + extraParams +
            '}';
    }

}
