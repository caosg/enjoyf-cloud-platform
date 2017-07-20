package com.enjoyf.platform.profileservice.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A VertualProfile.
 */
@Entity
@Table(name = "vertual_profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VertualProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;//equals userprofileid

    @NotNull
    @Column(name = "nick", nullable = false)
    @ApiModelProperty(value = "昵称",notes = "同userprofile中的昵称",required = true)
    private String nick;

    @Column(name = "description")
    @ApiModelProperty(value = "描述",required = true)
    private String description;

    @Column(name = "icon")
    @ApiModelProperty(value = "头像",required = true)
    private String icon;

    @Column(name = "sex")
    @ApiModelProperty(value = "性别",notes = "1-男，0-女",required = true)
    private Integer sex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getNick() {
        return nick;
    }

    public VertualProfile nick(String nick) {
        this.nick = nick;
        return this;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getDescription() {
        return description;
    }

    public VertualProfile discription(String discription) {
        this.description = discription;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public VertualProfile icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSex() {
        return sex;
    }

    public VertualProfile sex(Integer sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VertualProfile vertualProfile = (VertualProfile) o;
        if (vertualProfile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vertualProfile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VertualProfile{" +
            "id=" + getId() +
            ", nick='" + getNick() + "'" +
            ", discription='" + getDescription() + "'" +
            ", icon='" + getIcon() + "'" +
            ", sex='" + getSex() + "'" +
            "}";
    }
}
