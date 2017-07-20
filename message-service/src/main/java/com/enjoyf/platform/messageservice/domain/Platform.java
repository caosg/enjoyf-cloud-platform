package com.enjoyf.platform.messageservice.domain;

/**
 * Created by ericliu on 2017/5/26.
 */
public enum Platform {

    ANDROID(1), IOS(0);

    Platform(int code) {
        this.code = code;
    }

    private int code;

    public int getCode() {
        return code;
    }

    public static boolean isIOS(int platform) {
        return IOS.getCode() == platform;
    }

    public static boolean isAndroid(int platform) {
        return ANDROID.getCode() == platform;
    }

}
