package com.enjoyf.platform.contentservice.domain;

import com.enjoyf.platform.contentservice.domain.convert.GameExtJsonConvert;
import com.enjoyf.platform.contentservice.domain.enumeration.FeedbackType;
import com.enjoyf.platform.contentservice.domain.enumeration.ValidStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Game.
 */
@Entity
@Table(name = "game")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@RedisHash("_game_")
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "alias_name", nullable = false, columnDefinition = "")
    private String aliasName;

    @Column(name = "english_name", nullable = false, columnDefinition = "")
    private String englishName;

    @Column(name = "game_tag", nullable = false, columnDefinition = "")
    private String gameTag;

    @Column(name = "ext_json", nullable = false)
    @Convert(converter = GameExtJsonConvert.class)
    @JsonProperty(value = "extjson")
    private GameExtJson extJson;

    @Column(name = "valid_status", nullable = false)
    private String validStatus = ValidStatus.VALID.getCode();

    @Column(name = "create_time", nullable = false)
    private ZonedDateTime createTime = ZonedDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Game name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAliasName() {
        return aliasName;
    }

    public Game aliasName(String aliasName) {
        this.aliasName = aliasName;
        return this;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public Game englishName(String englishName) {
        this.englishName = englishName;
        return this;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getGameTag() {
        return gameTag;
    }

    public Game gameTag(String gameTag) {
        this.gameTag = gameTag;
        return this;
    }

    public void setGameTag(String gameTag) {
        this.gameTag = gameTag;
    }

    public GameExtJson getExtJson() {
        return extJson;
    }

    public Game extJson(GameExtJson extJson) {
        this.extJson = extJson;
        return this;
    }

    public void setExtJson(GameExtJson extJson) {
        this.extJson = extJson;
    }

    public String getValidStatus() {
        return validStatus;
    }

    public Game validStatus(String validStatus) {
        this.validStatus = validStatus;
        return this;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Game createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Game game = (Game) o;
        if (game.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), game.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Game{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", aliasName='" + getAliasName() + "'" +
            ", englishName='" + getEnglishName() + "'" +
            ", gameTag='" + getGameTag() + "'" +
            ", extJson='" + getExtJson() + "'" +
            ", validStatus='" + getValidStatus() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
