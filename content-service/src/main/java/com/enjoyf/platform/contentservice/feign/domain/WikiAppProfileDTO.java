package com.enjoyf.platform.contentservice.feign.domain;


/**
 * Created by ericliu on 2017/6/24.
 */
public class WikiAppProfileDTO {
    private long uid;
    private String nick;
    private String description = "";
    private String icon;
    private int sex;

    private Long userfulSum;
    private Long commentSum;
    private VerifyProfileType verifyProfileType = VerifyProfileType.UNVERIFY;

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
