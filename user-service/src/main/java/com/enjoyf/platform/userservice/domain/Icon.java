package com.enjoyf.platform.userservice.domain;

import java.io.Serializable;

/**
 * Created by shuguangcao on 2017/3/22.
 */
public class Icon implements Serializable{
    private Integer id;//排序使用
    private String icon;//头像

    public Icon() {
    }

    public Icon(Integer id, String icon) {
        this.id = id;
        this.icon = icon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Icon{" +
            "id=" + id +
            ", icon='" + icon + '\'' +
            '}';
    }
}
