package com.enjoyf.platform.event.message.wikiapp;

import com.enjoyf.platform.event.message.MessageEventConstants;

/**
 * Created by ericliu on 2017/5/10.
 */
public class WikiAppMessageFeedbackGameEvent extends WikiAppMessageEvent {

    public WikiAppMessageFeedbackGameEvent() {
        super(WikiAppMessageEventType.WIKI_APP_FEEDBACK_GAME, MessageEventConstants.BIND_KEY);
    }

    private String appkey;
    private long uid;

    private Long gameId;
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

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
