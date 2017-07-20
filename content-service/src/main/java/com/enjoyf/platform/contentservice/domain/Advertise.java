package com.enjoyf.platform.contentservice.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import com.enjoyf.platform.contentservice.domain.enumeration.AdvertiseDomain;

/**
 * A Advertise.
 */
@Entity
@Table(name = "advertise")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Advertise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "appkey")
    private String appkey;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "domain")
    private Integer domain;

    @Column(name = "platform")
    private Integer platform;

    @Column(name = "type")
    private Integer type;

    @Column(name = "target")
    private String target;

    @Column(name = "pic")
    private String pic;

    @Column(name = "extend")
    private String extend;

    @Column(name = "remove_status")
    private String removeStatus;

    @Column(name = "display_order")
    private Long displayOrder;

    @Column(name = "create_date")
    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppkey() {
        return appkey;
    }

    public Advertise appkey(String appkey) {
        this.appkey = appkey;
        return this;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getTitle() {
        return title;
    }

    public Advertise title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Advertise description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDomain() {
        return domain;
    }

    public Advertise domain(Integer domain) {
        this.domain = domain;
        return this;
    }

    public void setDomain(Integer domain) {
        this.domain = domain;
    }

    public Integer getPlatform() {
        return platform;
    }

    public Advertise platform(Integer platform) {
        this.platform = platform;
        return this;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public Integer getType() {
        return type;
    }

    public Advertise type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTarget() {
        return target;
    }

    public Advertise target(String target) {
        this.target = target;
        return this;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getPic() {
        return pic;
    }

    public Advertise pic(String pic) {
        this.pic = pic;
        return this;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getExtend() {
        return extend;
    }

    public Advertise extend(String extend) {
        this.extend = extend;
        return this;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getRemoveStatus() {
        return removeStatus;
    }

    public Advertise removeStatus(String removeStatus) {
        this.removeStatus = removeStatus;
        return this;
    }

    public void setRemoveStatus(String removeStatus) {
        this.removeStatus = removeStatus;
    }

    public Long getDisplayOrder() {
        return displayOrder;
    }

    public Advertise displayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
        return this;
    }

    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Advertise createDate(Date createDate) {
        this.createDate = createDate;
        return this;
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
        Advertise advertise = (Advertise) o;
        if (advertise.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), advertise.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Advertise{" +
            "id=" + getId() +
            ", appkey='" + getAppkey() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", domain='" + getDomain() + "'" +
            ", platform='" + getPlatform() + "'" +
            ", type='" + getType() + "'" +
            ", target='" + getTarget() + "'" +
            ", pic='" + getPic() + "'" +
            ", extend='" + getExtend() + "'" +
            ", removeStatus='" + getRemoveStatus() + "'" +
            ", displayOrder='" + getDisplayOrder() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            "}";
    }



}
