package com.enjoyf.platform.contentservice.domain.enumeration;

/**
 * Created by zhimingli on 2017/5/12.
 */
public enum ContentSource {

    //CMS文章
    CMS_ARCHIVE(1),

    //WIKI
    WIKI(2);


    private int code;

    ContentSource(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
