package com.enjoyf.platform.contentservice.domain.enumeration;

/**
 * Created by zhimingli on 2017/6/21.
 */
public enum GameLine {

    //按标签最新
    NEW_GAME_LIEN("contentservice:new_gametag_set:"),

    //首页游戏
    GAME_INDEX_SET("contentservice:game_index_set:"),

    //按标签最热
    HOT_GAME_LIEN("contentservice:hot_gametag_set:");


    GameLine(String code) {
        this.code = code;
    }

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
