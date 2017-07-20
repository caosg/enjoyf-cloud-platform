package com.enjoyf.platform.userservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Player.
 */
@Entity
@Table(name = "player")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 40)
    @Column(name = "profile_no", nullable = false)
    private String profileNo;

    @Column(name = "level_id")
    private Long levelId;

    @Column(name = "description")
    private String description;

    @Column(name = "point")
    private Integer point;

    @Column(name = "nick")
    @Size(max = 24)
    private String nick;

    @Size(max = 24)
    @Column(name = "app_key")
    private String appKey;

    @Transient
    @JsonProperty("levelName")
    private String levelName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfileNo() {
        return profileNo;
    }

    public Player profileNo(String profileNo) {
        this.profileNo = profileNo;
        return this;
    }

    public void setProfileNo(String profileNo) {
        this.profileNo = profileNo;
    }

    public Long getLevelId() {
        return levelId;
    }

    public Player levelId(Long levelId) {
        this.levelId = levelId;
        return this;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public String getDescription() {
        return description;
    }

    public Player description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPoint() {
        return point;
    }

    public Player point(Integer point) {
        this.point = point;
        return this;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getNick() {
        return nick;
    }

    public Player nick(String nick) {
        this.nick = nick;
        return this;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAppKey() {
        return appKey;
    }

    public Player appKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        if (player.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), player.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Player{" +
            "id=" + getId() +
            ", profileNo='" + getProfileNo() + "'" +
            ", levelId='" + getLevelId() + "'" +
            ", description='" + getDescription() + "'" +
            ", point='" + getPoint() + "'" +
            ", nick='" + getNick() + "'" +
            ", appKey='" + getAppKey() + "'" +
            "}";
    }
}
