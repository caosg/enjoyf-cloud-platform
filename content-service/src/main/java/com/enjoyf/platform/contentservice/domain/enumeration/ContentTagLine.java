package com.enjoyf.platform.contentservice.domain.enumeration;

/**
 * Created by zhimingli on 2017/6/7.
 */
public enum ContentTagLine {

    ////推荐位置
    RECOMMEND("wiki_recommend");


    ContentTagLine(String code) {
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
