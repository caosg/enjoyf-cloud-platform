package com.enjoyf.platform.autoconfigure.web;

import java.io.Serializable;

/**
 * http请求公共参数，从http header 头里传递
 * Created by shuguangcao on 2017/6/29.
 */
public class CommonParams implements Serializable {

    private static final long serialVersionUID = -3407683685791400991L;

    public static final String PLATFORM = "platform";//客户端平台(平台 0--iPhone,1--android，2--ipad)

    public static final String VERSION = "version";//客户端当前版本

    public static final String APPKEY = "appkey";//该应用appkey

    public static final String DEVICEID = "deviceid";//设备id

    public static final String CHANNEL = "channel";//渠道的code

    public static final String LOGIN_DOMAIN = "loginDomain";//用户登陆来源，qq/mobile/client等

    private int platform;

    private String version;

    private String appkey;

    private String deviceid;

    private String channel;

    private String loginDomain;

    public CommonParams() {
    }

    public CommonParams(int platform, String version, String appkey, String deviceid, String channel, String loginDomain) {
        this.platform = platform;
        this.version = version;
        this.appkey = appkey;
        this.deviceid = deviceid;
        this.channel = channel;
        this.loginDomain = loginDomain;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getLoginDomain() {
        return loginDomain;
    }

    public void setLoginDomain(String loginDomain) {
        this.loginDomain = loginDomain;
    }

    @Override
    public String toString() {
        return "CommonParams{" +
                "platform=" + platform +
                ", version='" + version + '\'' +
                ", appkey='" + appkey + '\'' +
                ", deviceid='" + deviceid + '\'' +
                ", channel='" + channel + '\'' +
                ", loginDomain='" + loginDomain + '\'' +
                '}';
    }
}
