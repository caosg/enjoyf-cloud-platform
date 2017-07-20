package com.enjoyf.platform.contentservice.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * A CommentRating.
 */
@Entity
@Table(name = "comment_rating")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommentRating implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "score_sum")
    private Double scoreSum=0.0;

    @Column(name = "score_num")
    private Integer scoreNum=0;

    @Column(name = "five_user_sum")
    private Integer fiveUserSum=0;

    @Column(name = "four_user_sum")
    private Integer fourUserSum=0;

    @Column(name = "three_user_sum")
    private Integer threeUserSum=0;

    @Column(name = "two_user_sum")
    private Integer twoUserSum=0;

    @Column(name = "one_user_sum")
    private Integer oneUserSum=0;

    @Column(name = "create_time")
    private Date createTime = new Date();

    @Column(name = "modify_time")
    private Date modifyTime = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public CommentRating gameId(Long gameId) {
        this.gameId = gameId;
        return this;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Double getScoreSum() {
        return scoreSum;
    }

    public CommentRating scoreSum(Double scoreSum) {
        this.scoreSum = scoreSum;
        return this;
    }

    public void setScoreSum(Double scoreSum) {
        this.scoreSum = scoreSum;
    }

    public Integer getScoreNum() {
        return scoreNum;
    }

    public CommentRating scoreNum(Integer scoreNum) {
        this.scoreNum = scoreNum;
        return this;
    }

    public void setScoreNum(Integer scoreNum) {
        this.scoreNum = scoreNum;
    }

    public Integer getFiveUserSum() {
        return fiveUserSum;
    }

    public CommentRating fiveUserSum(Integer fiveUserSum) {
        this.fiveUserSum = fiveUserSum;
        return this;
    }

    public void setFiveUserSum(Integer fiveUserSum) {
        this.fiveUserSum = fiveUserSum;
    }

    public Integer getFourUserSum() {
        return fourUserSum;
    }

    public CommentRating fourUserSum(Integer fourUserSum) {
        this.fourUserSum = fourUserSum;
        return this;
    }

    public void setFourUserSum(Integer fourUserSum) {
        this.fourUserSum = fourUserSum;
    }

    public Integer getThreeUserSum() {
        return threeUserSum;
    }

    public CommentRating threeUserSum(Integer threeUserSum) {
        this.threeUserSum = threeUserSum;
        return this;
    }

    public void setThreeUserSum(Integer threeUserSum) {
        this.threeUserSum = threeUserSum;
    }

    public Integer getTwoUserSum() {
        return twoUserSum;
    }

    public CommentRating twoUserSum(Integer twoUserSum) {
        this.twoUserSum = twoUserSum;
        return this;
    }

    public void setTwoUserSum(Integer twoUserSum) {
        this.twoUserSum = twoUserSum;
    }

    public Integer getOneUserSum() {
        return oneUserSum;
    }

    public CommentRating oneUserSum(Integer oneUserSum) {
        this.oneUserSum = oneUserSum;
        return this;
    }

    public void setOneUserSum(Integer oneUserSum) {
        this.oneUserSum = oneUserSum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public CommentRating createTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public CommentRating modifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommentRating commentRating = (CommentRating) o;
        if (commentRating.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commentRating.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommentRating{" +
            "id=" + getId() +
            ", gameId='" + getGameId() + "'" +
            ", scoreSum='" + getScoreSum() + "'" +
            ", scoreNum='" + getScoreNum() + "'" +
            ", fiveUserSum='" + getFiveUserSum() + "'" +
            ", fourUserSum='" + getFourUserSum() + "'" +
            ", threeUserSum='" + getThreeUserSum() + "'" +
            ", twoUserSum='" + getTwoUserSum() + "'" +
            ", oneUserSum='" + getOneUserSum() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            "}";
    }
}
