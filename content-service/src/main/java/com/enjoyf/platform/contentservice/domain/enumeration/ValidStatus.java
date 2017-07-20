package com.enjoyf.platform.contentservice.domain.enumeration;

public enum ValidStatus {
    VALID("valid"),
    INVALID("invalid"),
    REMOVED("removed");


    ValidStatus(String code) {
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
