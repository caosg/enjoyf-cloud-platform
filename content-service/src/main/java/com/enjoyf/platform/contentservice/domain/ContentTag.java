package com.enjoyf.platform.contentservice.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * A ContentTag.
 */
@Entity
@Table(name = "content_tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContentTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "target")
    private String target;

    @Column(name = "tag_type")
    private Integer tagType;

    @Column(name = "tag_line")
    private String tagLine;

    @Column(name = "display_order")
    private Long displayOrder;

    @Column(name = "valid_status")
    private String validStatus;

    @Column(name = "create_date")
    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ContentTag name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTarget() {
        return target;
    }

    public ContentTag target(String target) {
        this.target = target;
        return this;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getTagType() {
        return tagType;
    }

    public ContentTag tagType(Integer tagType) {
        this.tagType = tagType;
        return this;
    }

    public void setTagType(Integer tagType) {
        this.tagType = tagType;
    }

    public String getTagLine() {
        return tagLine;
    }

    public ContentTag tagLine(String tagLine) {
        this.tagLine = tagLine;
        return this;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public Long getDisplayOrder() {
        return displayOrder;
    }

    public ContentTag displayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
        return this;
    }

    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getValidStatus() {
        return validStatus;
    }

    public ContentTag validStatus(String validStatus) {
        this.validStatus = validStatus;
        return this;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }

    public ContentTag createDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContentTag contentTag = (ContentTag) o;
        if (contentTag.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contentTag.getId());
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContentTag{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", target='" + getTarget() + "'" +
            ", tagType='" + getTagType() + "'" +
            ", tagLine='" + getTagLine() + "'" +
            ", displayOrder='" + getDisplayOrder() + "'" +
            ", validStatus='" + getValidStatus() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            "}";
    }


}
