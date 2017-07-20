/*
 * userservice API
 * userservice API documentation
 *
 * OpenAPI spec version: 0.0.1
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.enjoyf.platform.userservice.client.model;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SocialAuthDTO
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2017-05-08T17:21:02.966+08:00")
public class SocialAuthDTO {
    @SerializedName("appKey")
    private String appKey = null;

    @SerializedName("createdIp")
    private String createdIp = null;

    @SerializedName("extraParams")
    private Map<String, String> extraParams = new HashMap<String, String>();

    @SerializedName("icon")
    private String icon = null;

    @SerializedName("login")
    private String login = null;

    @SerializedName("loginDomain")
    private String loginDomain = null;

    @SerializedName("nick")
    private String nick = null;

    @SerializedName("password")
    private String password = null;

    @SerializedName("profileKey")
    private String profileKey = null;

    @SerializedName("validCode")
    private String validCode = null;

    public SocialAuthDTO appKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    /**
     * Get appKey
     * @return appKey
     **/
    @ApiModelProperty(value = "")
    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public SocialAuthDTO createdIp(String createdIp) {
        this.createdIp = createdIp;
        return this;
    }

    /**
     * Get createdIp
     * @return createdIp
     **/
    @ApiModelProperty(value = "")
    public String getCreatedIp() {
        return createdIp;
    }

    public void setCreatedIp(String createdIp) {
        this.createdIp = createdIp;
    }

    public SocialAuthDTO extraParams(Map<String, String> extraParams) {
        this.extraParams = extraParams;
        return this;
    }

    public SocialAuthDTO putExtraParamsItem(String key, String extraParamsItem) {
        this.extraParams.put(key, extraParamsItem);
        return this;
    }

    /**
     * Get extraParams
     * @return extraParams
     **/
    @ApiModelProperty(value = "")
    public Map<String, String> getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(Map<String, String> extraParams) {
        this.extraParams = extraParams;
    }

    public SocialAuthDTO icon(String icon) {
        this.icon = icon;
        return this;
    }

    /**
     * Get icon
     * @return icon
     **/
    @ApiModelProperty(value = "")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public SocialAuthDTO login(String login) {
        this.login = login;
        return this;
    }

    /**
     * Get login
     * @return login
     **/
    @ApiModelProperty(required = true, value = "")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public SocialAuthDTO loginDomain(String loginDomain) {
        this.loginDomain = loginDomain;
        return this;
    }

    /**
     * Get loginDomain
     * @return loginDomain
     **/
    @ApiModelProperty(required = true, value = "")
    public String getLoginDomain() {
        return loginDomain;
    }

    public void setLoginDomain(String loginDomain) {
        this.loginDomain = loginDomain;
    }

    public SocialAuthDTO nick(String nick) {
        this.nick = nick;
        return this;
    }

    /**
     * Get nick
     * @return nick
     **/
    @ApiModelProperty(value = "")
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public SocialAuthDTO password(String password) {
        this.password = password;
        return this;
    }

    /**
     * Get password
     * @return password
     **/
    @ApiModelProperty(value = "")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SocialAuthDTO profileKey(String profileKey) {
        this.profileKey = profileKey;
        return this;
    }

    /**
     * Get profileKey
     * @return profileKey
     **/
    @ApiModelProperty(required = true, value = "")
    public String getProfileKey() {
        return profileKey;
    }

    public void setProfileKey(String profileKey) {
        this.profileKey = profileKey;
    }

    public SocialAuthDTO validCode(String validCode) {
        this.validCode = validCode;
        return this;
    }

    /**
     * Get validCode
     * @return validCode
     **/
    @ApiModelProperty(value = "")
    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SocialAuthDTO socialAuthDTO = (SocialAuthDTO) o;
        return Objects.equals(this.appKey, socialAuthDTO.appKey) &&
                Objects.equals(this.createdIp, socialAuthDTO.createdIp) &&
                Objects.equals(this.extraParams, socialAuthDTO.extraParams) &&
                Objects.equals(this.icon, socialAuthDTO.icon) &&
                Objects.equals(this.login, socialAuthDTO.login) &&
                Objects.equals(this.loginDomain, socialAuthDTO.loginDomain) &&
                Objects.equals(this.nick, socialAuthDTO.nick) &&
                Objects.equals(this.password, socialAuthDTO.password) &&
                Objects.equals(this.profileKey, socialAuthDTO.profileKey) &&
                Objects.equals(this.validCode, socialAuthDTO.validCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appKey, createdIp, extraParams, icon, login, loginDomain, nick, password, profileKey, validCode);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SocialAuthDTO {\n");

        sb.append("    appKey: ").append(toIndentedString(appKey)).append("\n");
        sb.append("    createdIp: ").append(toIndentedString(createdIp)).append("\n");
        sb.append("    extraParams: ").append(toIndentedString(extraParams)).append("\n");
        sb.append("    icon: ").append(toIndentedString(icon)).append("\n");
        sb.append("    login: ").append(toIndentedString(login)).append("\n");
        sb.append("    loginDomain: ").append(toIndentedString(loginDomain)).append("\n");
        sb.append("    nick: ").append(toIndentedString(nick)).append("\n");
        sb.append("    password: ").append(toIndentedString(password)).append("\n");
        sb.append("    profileKey: ").append(toIndentedString(profileKey)).append("\n");
        sb.append("    validCode: ").append(toIndentedString(validCode)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}

