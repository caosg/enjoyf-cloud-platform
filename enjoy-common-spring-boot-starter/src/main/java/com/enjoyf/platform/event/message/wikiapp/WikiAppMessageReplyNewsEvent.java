package com.enjoyf.platform.event.message.wikiapp;

import com.enjoyf.platform.event.Event;
import com.enjoyf.platform.event.EventType;
import com.enjoyf.platform.event.message.MessageEventConstants;
import com.enjoyf.platform.event.message.MessageEventType;

/**
 * Created by ericliu on 2017/5/10.
 */
public class WikiAppMessageReplyNewsEvent extends WikiAppMessageEvent {

    public WikiAppMessageReplyNewsEvent() {
        super(WikiAppMessageEventType.WIKI_APP_REPLY_NEWS, MessageEventConstants.BIND_KEY);
    }

    private String appkey;
    private Long uid;

    private String title;
    private String url;
    private Long replyUid;
    private Long replyId;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getReplyUid() {
        return replyUid;
    }

    public void setReplyUid(Long replyUid) {
        this.replyUid = replyUid;
    }

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    @Override
    public String toString() {
        return "WikiAppMessageReplyNewsEvent{" +
                "appkey='" + appkey + '\'' +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", replyUid=" + replyUid +
                ", replyId=" + replyId +
                '}';
    }
}
