package com.enjoyf.platform.event.message.wikiapp;

import com.enjoyf.platform.event.message.MessageEventConstants;

/**
 * Created by ericliu on 2017/5/10.
 */
public class WikiAppMessageFeedbackUserEvent extends WikiAppMessageEvent {

    public WikiAppMessageFeedbackUserEvent() {
        super(WikiAppMessageEventType.WIKI_APP_FEEDBACK_USER, MessageEventConstants.BIND_KEY);
    }

    private String appkey;
    private long uid;

    private Long feedbackUid;
    private String body;


    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public Long getFeedbackUid() {
        return feedbackUid;
    }

    public void setFeedbackUid(Long feedbackUid) {
        this.feedbackUid = feedbackUid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
