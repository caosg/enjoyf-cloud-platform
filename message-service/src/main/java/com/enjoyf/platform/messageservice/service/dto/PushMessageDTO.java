package com.enjoyf.platform.messageservice.service.dto;

import com.enjoyf.platform.event.message.enumration.PushType;

import java.util.Map;

/**
 * Created by ericliu on 2017/5/27.
 */
public class PushMessageDTO {
    private String title;
    private int platform;
    private String body;
    private String device;
    private String tags;
    private PushType pushType;//0-false 1-all
    private Map<String, String> extras;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public PushType getPushType() {
        return pushType;
    }

    public void setPushType(PushType pushType) {
        this.pushType = pushType;
    }

    public Map<String, String> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, String> extras) {
        this.extras = extras;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "PushMessageDTO{" +
            "title='" + title + '\'' +
            ", platform=" + platform +
            ", body='" + body + '\'' +
            ", device='" + device + '\'' +
            ", tags='" + tags + '\'' +
            ", pushType=" + pushType +
            ", extras=" + extras +
            '}';
    }
}
