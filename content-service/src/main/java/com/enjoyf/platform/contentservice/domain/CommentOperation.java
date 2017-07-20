package com.enjoyf.platform.contentservice.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * A CommentOperation.
 * 点评操作表 2017-07-11 暂时只有对点评点赞操作记录
 */
@Entity
@Table(name = "comment_operation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommentOperation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_id")
    private Long commentId;//commentId

    @Column(name = "uid")
    private Long uid;//操作人

    @Column(name = "dest_uid")
    private Long destUid;//被操作人

    @Column(name = "operate_type")
    private Integer operateType;//操作类型

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommentId() {
        return commentId;
    }

    public CommentOperation commentId(Long commentId) {
        this.commentId = commentId;
        return this;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getUid() {
        return uid;
    }

    public CommentOperation uid(Long uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getDestUid() {
        return destUid;
    }

    public CommentOperation destUid(Long destUid) {
        this.destUid = destUid;
        return this;
    }

    public void setDestUid(Long destUid) {
        this.destUid = destUid;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public CommentOperation operateType(Integer operateType) {
        this.operateType = operateType;
        return this;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public CommentOperation createTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public CommentOperation updateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommentOperation commentOperation = (CommentOperation) o;
        if (commentOperation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commentOperation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommentOperation{" +
            "id=" + getId() +
            ", commentId='" + getCommentId() + "'" +
            ", uid='" + getUid() + "'" +
            ", destUid='" + getDestUid() + "'" +
            ", operateType='" + getOperateType() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
