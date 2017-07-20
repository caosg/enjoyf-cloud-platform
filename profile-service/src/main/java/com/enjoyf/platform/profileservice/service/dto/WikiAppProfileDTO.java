package com.enjoyf.platform.profileservice.service.dto;

import com.enjoyf.platform.profileservice.domain.VerifyProfile;
import com.enjoyf.platform.profileservice.domain.enumeration.VerifyProfileType;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

/**
 * Created by ericliu on 2017/6/24.
 */
public class WikiAppProfileDTO {

    @ApiModelProperty(value = "uid",required = true)
    private long uid;
    @ApiModelProperty(hidden = true)
    private String nick;
    @ApiModelProperty(hidden = true)
    private String description = "";
    @ApiModelProperty(hidden = true)
    private String icon;
    @ApiModelProperty(hidden = true)
    private Long userfulSum;
    @ApiModelProperty(hidden = true)
    private Long commentSum;
    @ApiModelProperty(hidden = true)
    private VerifyProfileType verifyProfileType = VerifyProfileType.UNVERIFY;

    private int sex;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getUserfulSum() {
        return userfulSum;
    }

    public void setUserfulSum(Long userfulSum) {
        this.userfulSum = userfulSum;
    }

    public Long getCommentSum() {
        return commentSum;
    }

    public void setCommentSum(Long commentSum) {
        this.commentSum = commentSum;
    }

    public VerifyProfileType getVerifyProfileType() {
        return verifyProfileType;
    }

    public void setVerifyProfileType(VerifyProfileType verifyProfileType) {
        this.verifyProfileType = verifyProfileType;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "WikiAppUserProfileDTO{" +
            "uid=" + uid +
            ", nick='" + nick + '\'' +
            ", description='" + description + '\'' +
            ", icon='" + icon + '\'' +
            ", userfulSum=" + userfulSum +
            ", commentSum=" + commentSum +
            ", verifyProfileType=" + verifyProfileType +
            '}';
    }
}
