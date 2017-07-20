package com.enjoyf.platform.contentservice.domain;

import com.enjoyf.platform.contentservice.domain.enumeration.ValidStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * A Comment.
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "uid")
    private Long uid;

    @Column(name = "score")
    private Integer score = 0;

    @Column(name = "body")
    private String body;

    @Column(name = "agree_num")
    private Integer agreeNum = 0;

    @Column(name = "create_time")
    private Date createTime = new Date();

    @Column(name = "modify_time")
    private Date modifyTime = new Date();

    @Column(name = "valid_status")
    private String validStatus = ValidStatus.VALID.getCode();

    @Column(name = "high_quality")
    private Integer highQuality = 0;

    @Column(name = "reply_num")
    private Integer replyNum = 0;

    @Column(name = "real_score")
    private Double real_score = 0.0;//用户的真实评分 认证用户为score*1  普通用户为 score*0.8

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public Comment gameId(Long gameId) {
        this.gameId = gameId;
        return this;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getUid() {
        return uid;
    }

    public Comment uid(Long uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getScore() {
        return score;
    }

    public Comment score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getBody() {
        return body;
    }

    public Comment body(String body) {
        this.body = body;
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getAgreeNum() {
        return agreeNum;
    }

    public Comment agreeNum(Integer agreeNum) {
        this.agreeNum = agreeNum;
        return this;
    }

    public void setAgreeNum(Integer agreeNum) {
        this.agreeNum = agreeNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Comment createTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public Comment modifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getValidStatus() {
        return validStatus;
    }

    public Comment validStatus(String validStatus) {
        this.validStatus = validStatus;
        return this;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }

    public Integer getHighQuality() {
        return highQuality;
    }

    public Comment highQuality(Integer highQuality) {
        this.highQuality = highQuality;
        return this;
    }

    public void setHighQuality(Integer highQuality) {
        this.highQuality = highQuality;
    }

    public Integer getReplyNum() {
        return replyNum;
    }

    public Comment replyNum(Integer replyNum) {
        this.replyNum = replyNum;
        return this;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    public Double getReal_score() {
        return real_score;
    }

    public Comment real_score(Double real_score) {
        this.real_score = real_score;
        return this;
    }

    public void setReal_score(Double real_score) {
        this.real_score = real_score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        if (comment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", gameId='" + getGameId() + "'" +
            ", uid='" + getUid() + "'" +
            ", score='" + getScore() + "'" +
            ", body='" + getBody() + "'" +
            ", agreeNum='" + getAgreeNum() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            ", validStatus='" + getValidStatus() + "'" +
            ", highQuality='" + getHighQuality() + "'" +
            ", replyNum='" + getReplyNum() + "'" +
            ", real_score='" + getReal_score() + "'" +
            "}";
    }
}
