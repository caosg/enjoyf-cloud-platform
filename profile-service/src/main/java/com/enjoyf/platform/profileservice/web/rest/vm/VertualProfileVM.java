package com.enjoyf.platform.profileservice.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by ericliu on 2017/6/16.
 */
public class VertualProfileVM {
    private String appkey="default";
    private String discription;
    private String icon;
    private String nick;
    private String profileKey = "www";
    private int sex;
    @JsonIgnore
    private int flag = 0;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getProfileKey() {
        return profileKey;
    }

    public void setProfileKey(String profileKey) {
        this.profileKey = profileKey;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "VertualProfileVM{" +
            "appkey='" + appkey + '\'' +
            ", discription='" + discription + '\'' +
            ", icon='" + icon + '\'' +
            ", nick='" + nick + '\'' +
            ", profileKey='" + profileKey + '\'' +
            ", sex=" + sex +
            ", flag=" + flag +
            '}';
    }
}
