package com.enjoyf.platform.contentservice.domain.enumeration;

/**
 * Created by zhimingli on 2017/5/12.
 */
public enum ContentLineType {

    //CMS文章
    CONTENTLINE_ARCHIVE(1),

    //WIKI
    CONTENTLINE_WIKI(2);


    private int code;

    ContentLineType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
