package com.enjoyf.platform.event.message;

import com.enjoyf.platform.event.Event;

/**
 * Created by ericliu on 2017/5/10.
 */
public class MessageBreakProfileDeviceEvent extends Event {

    public MessageBreakProfileDeviceEvent() {
        super(MessageEventType.CREATE_DEVICERELATION, MessageEventConstants.BIND_KEY);
    }

    private String appkey;
    private int platform;
    private String profileNo;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getProfileNo() {
        return profileNo;
    }

    public void setProfileNo(String profileNo) {
        this.profileNo = profileNo;
    }

    @Override
    public String toString() {
        return "MessageCreateProfileDeviceEvent{" +
                "appkey='" + appkey + '\'' +
                ", platform=" + platform +
                ", profileNo='" + profileNo + '\'' +
                '}';
    }
}
