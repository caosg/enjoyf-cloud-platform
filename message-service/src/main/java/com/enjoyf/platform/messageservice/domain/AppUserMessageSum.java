package com.enjoyf.platform.messageservice.domain;

/**
 * Created by ericliu on 2017/6/24.
 */
public class AppUserMessageSum {
    private String appKey;
    private Long uid;

    private Long sum;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }
}
