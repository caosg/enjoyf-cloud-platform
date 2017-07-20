package com.enjoyf.platform.contentservice.domain.enumeration;

/**
 * Created by zhimingli on 2017/5/12.
 */
public enum ContentLineOwn {

    //CMS所有文章
    ALL_ARCHIVE(1),

    //游戏下所有文章
    GAME_ALL_ARCHIVE(2),

    //今天发的所有文章
    TODAY_ALL_ARCHIVE(3);


    private int code;

    ContentLineOwn(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
