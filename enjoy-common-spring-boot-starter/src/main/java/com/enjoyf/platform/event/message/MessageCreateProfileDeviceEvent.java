package com.enjoyf.platform.event.message;

import com.enjoyf.platform.event.Event;
import com.enjoyf.platform.event.user.UserEventConstants;
import com.enjoyf.platform.event.user.UserEventType;

/**
 * Created by ericliu on 2017/5/10.
 */
public class MessageCreateProfileDeviceEvent extends Event {

    public MessageCreateProfileDeviceEvent() {
        super(MessageEventType.CREATE_DEVICERELATION, MessageEventConstants.BIND_KEY);
    }

    private String appkey;
    private int platform;
    private String profileNo;
    private String device;

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

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "MessageCreateProfileDeviceEvent{" +
                "appkey='" + appkey + '\'' +
                ", platform=" + platform +
                ", profileNo='" + profileNo + '\'' +
                ", device='" + device + '\'' +
                '}';
    }
}
