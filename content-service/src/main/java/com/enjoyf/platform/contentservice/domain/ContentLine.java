package com.enjoyf.platform.contentservice.domain;

import com.enjoyf.platform.contentservice.domain.enumeration.ContentLineType;
import com.enjoyf.platform.contentservice.domain.enumeration.ValidStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A ContentLine.
 */
@Entity
@Table(name = "content_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContentLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "line_key")
    private String linekey;

    @Column(name = "own_id")
    private Integer ownId;

    @Column(name = "line_type")
    private Integer lineType = ContentLineType.CONTENTLINE_ARCHIVE.getCode();

    @Column(name = "dest_id")
    private Long destId;

    @Column(name = "score")
    private Double score;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "valid_status")
    private String validStatus = ValidStatus.VALID.getCode();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLinekey() {
        return linekey;
    }

    public ContentLine linekey(String linekey) {
        this.linekey = linekey;
        return this;
    }

    public void setLinekey(String linekey) {
        this.linekey = linekey;
    }

    public Integer getOwnId() {
        return ownId;
    }

    public ContentLine ownId(Integer ownId) {
        this.ownId = ownId;
        return this;
    }

    public void setOwnId(Integer ownId) {
        this.ownId = ownId;
    }

    public Integer getLineType() {
        return lineType;
    }

    public ContentLine lineType(Integer lineType) {
        this.lineType = lineType;
        return this;
    }

    public void setLineType(Integer lineType) {
        this.lineType = lineType;
    }

    public Long getDestId() {
        return destId;
    }

    public ContentLine destId(Long destId) {
        this.destId = destId;
        return this;
    }

    public void setDestId(Long destId) {
        this.destId = destId;
    }

    public Double getScore() {
        return score;
    }

    public ContentLine score(Double score) {
        this.score = score;
        return this;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ContentLine createTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getValidStatus() {
        return validStatus;
    }

    public ContentLine validStatus(String validStatus) {
        this.validStatus = validStatus;
        return this;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContentLine contentLine = (ContentLine) o;
        if (contentLine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contentLine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContentLine{" +
            "id=" + getId() +
            ", linekey='" + getLinekey() + "'" +
            ", ownId='" + getOwnId() + "'" +
            ", lineType='" + getLineType() + "'" +
            ", destId='" + getDestId() + "'" +
            ", score='" + getScore() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", validStatus='" + getValidStatus() + "'" +
            "}";
    }
}
