package com.enjoyf.platform.contentservice.domain.enumeration;

/**
 * Created by zhimingli on 2017/5/12.
 */
public enum WikiappSearchType {


    GAME(1),

    ARCHIVE(2);


    private int code;

    WikiappSearchType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
