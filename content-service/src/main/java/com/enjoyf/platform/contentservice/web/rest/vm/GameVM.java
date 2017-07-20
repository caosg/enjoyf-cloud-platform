package com.enjoyf.platform.contentservice.web.rest.vm;

import com.enjoyf.platform.contentservice.service.dto.GameTagDTO;

import java.util.List;

/**
 * Created by zhimingli on 2017/6/21.
 * 游戏描述
 */
public class GameVM {
    private Long id;
    private String name;
    private List<GameTagDTO> gameTag;
    private String gameLogo = "";
    private String gameDeveloper = "";//开发商
    private Double score = 0D;
    private Integer scoreSum = 0;
    private String recommend = "";//一句话推荐
    private String recommendAuth;//一句话推荐作者
    private Long createTime;//创建时间
    private boolean hasComment = false;

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

    public String getGameDeveloper() {
        return gameDeveloper;
    }

    public void setGameDeveloper(String gameDeveloper) {
        this.gameDeveloper = gameDeveloper;
    }
}
