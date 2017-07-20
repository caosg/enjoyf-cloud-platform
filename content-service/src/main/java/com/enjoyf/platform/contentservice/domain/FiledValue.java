package com.enjoyf.platform.contentservice.domain;

/**
 * Created by zhimingli on 2017/6/1.
 */
public class FiledValue {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FiledValue(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
