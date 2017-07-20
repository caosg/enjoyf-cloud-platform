package com.enjoyf.platform.userservice.service.dto;

import com.enjoyf.platform.userservice.service.util.UserSeviceUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;

/**
 * Created by shuguangcao on 2017/3/18.
 */
@ApiModel(value="RegisterReqDTO", description="注册参数")
public class RegisterReqDTO {

    private String login,nick , validCode,icon, loginDomain,profileKey,createdIp,appKey="default";
    private String password= UserSeviceUtil.DEFAULT_PASSWORD;
    private Map<String,String> extraParams;

    public RegisterReqDTO() {
    }

    public RegisterReqDTO(String login, String password, String nick, String validCode,String icon, String loginDomain, String profileKey, String createdIp, String appKey,
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
    @ApiModelProperty(value = "登陆账号", notes = "手机号",required = true)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    @NotNull
    @ApiModelProperty(value = "登陆密码", notes = "登陆密码，不能少于6位",required = true)
    public String getPassword() {
        return StringUtils.isEmpty(password)?UserSeviceUtil.DEFAULT_PASSWORD:password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull
    @Size(min=2,max=24)
    @ApiModelProperty(value = "昵称",required = true)
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @NotNull
    @Size(min = 1,max = 32)
    @ApiModelProperty(value = "登陆方式",notes = "默认mobile",allowableValues = "mobile,client,email",required = true)
    public String getLoginDomain() {
        return loginDomain;
    }

    public void setLoginDomain(String loginDomain) {
        this.loginDomain = loginDomain;
    }

    //s@NotNull
    @Size(min = 2,max = 16)
    @ApiModelProperty(value = "类型", allowableValues = "www",required = true)
    public String getProfileKey() {
        return profileKey;
    }

    public void setProfileKey(String profileKey) {
        this.profileKey = profileKey;
    }

    @ApiModelProperty(value = "注册IP")
    public String getCreatedIp() {
        return createdIp;
    }

    public void setCreatedIp(String createdIp) {
        this.createdIp = createdIp;
    }

    @ApiModelProperty(value = "应用Key",allowableValues = "default",required = true)
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

    @ApiModelProperty(value = "额外参数,键值形式传递")
    public Map<String, String> getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(Map<String, String> extraParams) {
        this.extraParams = extraParams;
    }

    @ApiModelProperty(value = "手机验证码,只有手机号注册时需要提供")
    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

    @Override
    public String toString() {
        return "RegisterReqDTO{" +
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
