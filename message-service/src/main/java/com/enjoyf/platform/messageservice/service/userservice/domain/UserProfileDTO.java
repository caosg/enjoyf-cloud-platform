package com.enjoyf.platform.messageservice.service.userservice.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A UserProfileDTO.
 */

public class UserProfileDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;
    private String nick;
    private String icon;
    private Integer sex = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
