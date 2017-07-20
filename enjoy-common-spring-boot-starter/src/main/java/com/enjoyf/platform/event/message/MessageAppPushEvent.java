package com.enjoyf.platform.event.message;

import com.enjoyf.platform.event.Event;
import com.enjoyf.platform.event.message.enumration.PushType;

/**
 * Created by ericliu on 2017/5/10.
 */
public class MessageAppPushEvent extends Event {

    public MessageAppPushEvent() {
        super(MessageEventType.APP_PUSH_MESSAGE, MessageEventConstants.BIND_KEY);
    }

    private String appkey;
    private Long pushUid;
    private String title;
    private String body;
    private String tags;
    private PushType pushType;
    private int jt;
    private String ji;
    private Long pushMessageId;

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

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public Long getPushUid() {
        return pushUid;
    }

    public void setPushUid(Long pushUid) {
        this.pushUid = pushUid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PushType getPushType() {
        return pushType;
    }

    public void setPushType(PushType pushType) {
        this.pushType = pushType;
    }

    public int getJt() {
        return jt;
    }

    public void setJt(int jt) {
        this.jt = jt;
    }

    public String getJi() {
        return ji;
    }

    public void setJi(String ji) {
        this.ji = ji;
    }

    public Long getPushMessageId() {
        return pushMessageId;
    }

    public void setPushMessageId(Long pushMessageId) {
        this.pushMessageId = pushMessageId;
    }

    @Override
    public String toString() {
        return "MessageAppPushEvent{" +
                "appkey='" + appkey + '\'' +
                ", pushUid=" + pushUid +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", tags=" + tags +
                ", pushType=" + pushType +
                ", jt=" + jt +
                ", ji=" + ji +
                '}';
    }
}
