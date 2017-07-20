package com.enjoyf.platform.contentservice.domain;

import com.enjoyf.platform.common.domain.enumeration.ValidStatus;
import com.enjoyf.platform.contentservice.domain.enumeration.FeedbackType;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Feedback.
 */
@Entity
@Table(name = "feedback")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "uid", nullable = false)
    @ApiModelProperty(hidden = true)
    private Long uid;

    @NotNull
    @Column(name = "feedback_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @ApiModelProperty("举报类型")
    private FeedbackType feedbackType;

    @NotNull
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @ApiModelProperty(value = "处理状态", required = false)
    private ValidStatus status = ValidStatus.INIT;

    @NotNull
    @Column(name = "description", nullable = false)
    @ApiModelProperty(value = "举报描述，用,分割")
    private String description;

    @NotNull
    @Column(name = "create_time", nullable = false)
    @ApiModelProperty(hidden = true)
    private ZonedDateTime createTime;

    @Column(name = "create_ip")
    private String create_ip;

    @NotNull
    @Size(max = 36)
    @Column(name = "appkey", length = 36, nullable = false)
    @ApiModelProperty(value = "举报来源的appkey")
    private String appkey;

    @Column(name = "destid")
    @ApiModelProperty(value = "举报对象的id")
    private Long destid = 0l;


    @Column(name = "dest_body")
    @ApiModelProperty(value = "举报对象的文本信息，游戏名称、评论内容用于后台显示")
    private String destBody = "";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public Feedback uid(Long uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public FeedbackType getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(FeedbackType feedbackType) {
        this.feedbackType = feedbackType;
    }

    public Feedback feedbackType(FeedbackType feedbackType) {
        this.feedbackType = feedbackType;
        return this;
    }


    public String getDescription() {
        return description;
    }

    public Feedback description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Feedback createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public String getCreate_ip() {
        return create_ip;
    }

    public Feedback create_ip(String create_ip) {
        this.create_ip = create_ip;
        return this;
    }

    public void setCreate_ip(String create_ip) {
        this.create_ip = create_ip;
    }

    public String getAppkey() {
        return appkey;
    }

    public Feedback appkey(String appkey) {
        this.appkey = appkey;
        return this;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public Long getDestid() {
        return destid;
    }

    public Feedback destid(Long destid) {
        this.destid = destid;
        return this;
    }

    public void setDestid(Long destid) {
        this.destid = destid;
    }

    public ValidStatus getStatus() {
        return status;
    }

    public void setStatus(ValidStatus status) {
        this.status = status;
    }

    public String getDestBody() {
        return destBody;
    }

    public void setDestBody(String destBody) {
        this.destBody = destBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Feedback feedback = (Feedback) o;
        if (feedback.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), feedback.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Feedback{" +
            "id=" + getId() +
            ", uid='" + getUid() + "'" +
            ", reason='" + getFeedbackType() + "'" +
            ", description='" + getDescription() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", create_ip='" + getCreate_ip() + "'" +
            ", appkey='" + getAppkey() + "'" +
            ", destid='" + getDestid() + "'" +
            "}";
    }
}
