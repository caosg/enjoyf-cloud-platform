package com.enjoyf.platform.contentservice.web.rest.vm;

import com.enjoyf.platform.contentservice.service.dto.GameTagDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhimingli on 2017/7/12.
 * 游戏详情描述VM
 */
public class GameDetailVM {

    private Long id;
    private String name;
    private List<GameTagDTO> gameTag;
    private String gameLogo = "";
    private String gameDeveloper = "";//开发商
    private Double score = 0D;
    private Integer scoreSum = 0;
    private String video = "";
    private String recommend = "";//一句话推荐
    private String recommendAuth;//一句话推荐作者
    private Long createTime;//创建时间
    private String backPic = "";
    private List<String> picList = new ArrayList<>();
    private boolean hasComment = false;
    private String price;
    private boolean vpn = false;
    private List<String> language = new ArrayList<>();
    private String iosDownload;
    private String androidDownload;
    private String gameDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GameTagDTO> getGameTag() {
        return gameTag;
    }

    public void setGameTag(List<GameTagDTO> gameTag) {
        this.gameTag = gameTag;
    }

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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getBackPic() {
        return backPic;
    }

    public void setBackPic(String backPic) {
        this.backPic = backPic;
    }

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }

    public String getRecommendAuth() {
        return recommendAuth;
    }

    public void setRecommendAuth(String recommendAuth) {
        this.recommendAuth = recommendAuth;
    }

    public boolean isHasComment() {
        return hasComment;
    }

    public void setHasComment(boolean hasComment) {
        this.hasComment = hasComment;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isVpn() {
        return vpn;
    }

    public void setVpn(boolean vpn) {
        this.vpn = vpn;
    }

    public List<String> getLanguage() {
        return language;
    }

    public void setLanguage(List<String> language) {
        this.language = language;
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


    public String getGameDesc() {
        return gameDesc;
    }

    public void setGameDesc(String gameDesc) {
        this.gameDesc = gameDesc;
    }
}
