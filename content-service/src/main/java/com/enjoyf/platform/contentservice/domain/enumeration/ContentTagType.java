package com.enjoyf.platform.contentservice.domain.enumeration;

/**
 * Created by zhimingli on 2017/5/12.
 */
public enum ContentTagType {
    //文章
    ARCHIVE(1),


    //wap页面
    WAP(2);


    private int code;

    ContentTagType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
