package com.enjoyf.platform.contentservice.domain.contentsum;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A ContentSum.
 */
@Entity
@Table(name = "content_sum")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContentSum implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "agree_num")
    private Integer agree_num;

    @Column(name = "create_date")
    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAgree_num() {
        return agree_num;
    }

    public ContentSum agree_num(Integer agree_num) {
        this.agree_num = agree_num;
        return this;
    }

    public void setAgree_num(Integer agree_num) {
        this.agree_num = agree_num;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public ContentSum createDate(Date createDate) {
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
        ContentSum contentSum = (ContentSum) o;
        if (contentSum.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contentSum.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContentSum{" +
            "id=" + getId() +
            ", agree_num='" + getAgree_num() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            "}";
    }
}
