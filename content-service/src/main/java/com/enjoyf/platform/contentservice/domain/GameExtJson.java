package com.enjoyf.platform.contentservice.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhimingli on 2017/6/20.
 */
public class GameExtJson implements Serializable {

    private String gameLogo; //游戏LOGO
    private String gameDeveloper;//开发商
    private Double score = 0D;
    private Integer scoreSum = 0;//评价人数
    private String price;
    private String iosDownload;
    private String androidDownload;
    private String video;
    private String pic; //图片宣传图，多张以逗号分隔

    private String gameDesc;
    private boolean vpn = false;//是否需要vpn false-不需要 true需要

    private String createUser; //创建人
    private String recommend;//一句话推荐
    private String recommendAuth;//一句话推荐作者
    private String backPic;//背景图

    private String language;//语言,多张以逗号分隔 ： 中文 英文 日文 其他

    public String getGameLogo() {
        return gameLogo;
    }

    public void setGameLogo(String gameLogo) {
        this.gameLogo = gameLogo;
    }

    public String getGameDeveloper() {
        return gameDeveloper;
    }

    public void setGameDeveloper(String gameDeveloper) {
        this.gameDeveloper = gameDeveloper;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getScoreSum() {
        return scoreSum;
    }

    public void setScoreSum(Integer scoreSum) {
        this.scoreSum = scoreSum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIosDownload() {
        return iosDownload;
    }

    public void setIosDownload(String iosDownload) {
        this.iosDownload = iosDownload;
    }

    public String getAndroidDownload() {
        return androidDownload;
    }

    public void setAndroidDownload(String androidDownload) {
        this.androidDownload = androidDownload;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getGameDesc() {
        return gameDesc;
    }

    public void setGameDesc(String gameDesc) {
        this.gameDesc = gameDesc;
    }

    public boolean isVpn() {
        return vpn;
    }

    public void setVpn(boolean vpn) {
        this.vpn = vpn;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getRecommendAuth() {
        return recommendAuth;
    }

    public void setRecommendAuth(String recommendAuth) {
        this.recommendAuth = recommendAuth;
    }

    public String getBackPic() {
        return backPic;
    }

    public void setBackPic(String backPic) {
        this.backPic = backPic;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
