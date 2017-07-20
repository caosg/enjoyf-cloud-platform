package com.enjoyf.platform.contentservice.service.dto;

/**
 * Created by pengxu on 2017/6/30.
 */
public class GameRatingDTO {
    private Long id;
    private Long gameId;
    private String gameName;
    private String gameIcon;
    private Double scoreSum;
    private Integer scoreNum;
    private Integer fiveUserSum;
    private Integer fourUserSum;
    private Integer threeUserSum;
    private Integer twoUserSum;
    private Integer oneUserSum;
    private long createTime;
    private long modifyTime;
    private boolean hasComment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameIcon() {
        return gameIcon;
    }

    public void setGameIcon(String gameIcon) {
        this.gameIcon = gameIcon;
    }

    public Double getScoreSum() {
        return scoreSum;
    }

    public void setScoreSum(Double scoreSum) {
        this.scoreSum = scoreSum;
    }

    public Integer getScoreNum() {
        return scoreNum;
    }

    public void setScoreNum(Integer scoreNum) {
        this.scoreNum = scoreNum;
    }

    public Integer getFiveUserSum() {
        return fiveUserSum;
    }

    public void setFiveUserSum(Integer fiveUserSum) {
        this.fiveUserSum = fiveUserSum;
    }

    public Integer getFourUserSum() {
        return fourUserSum;
    }

    public void setFourUserSum(Integer fourUserSum) {
        this.fourUserSum = fourUserSum;
    }

    public Integer getThreeUserSum() {
        return threeUserSum;
    }

    public void setThreeUserSum(Integer threeUserSum) {
        this.threeUserSum = threeUserSum;
    }

    public Integer getTwoUserSum() {
        return twoUserSum;
    }

    public void setTwoUserSum(Integer twoUserSum) {
        this.twoUserSum = twoUserSum;
    }

    public Integer getOneUserSum() {
        return oneUserSum;
    }

    public void setOneUserSum(Integer oneUserSum) {
        this.oneUserSum = oneUserSum;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public boolean isHasComment() {
        return hasComment;
    }

    public void setHasComment(boolean hasComment) {
        this.hasComment = hasComment;
    }
}
