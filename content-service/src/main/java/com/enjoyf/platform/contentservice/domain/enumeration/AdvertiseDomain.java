package com.enjoyf.platform.contentservice.domain.enumeration;

/**
 * Created by zhimingli on 2017/5/12.
 */
public enum AdvertiseDomain {
    //推荐标签轮播
    CAROUSEL(1),

    //推荐列表广告
    ARTICLE_ADVERTISE(2);


    private int code;

    AdvertiseDomain(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
