package com.enjoyf.platform.event.message.wikiapp;

import com.enjoyf.platform.event.EventType;

/**
 * Created by ericliu on 2017/5/10.
 */
public class WikiAppMessageEventType extends EventType {

    public static final WikiAppMessageEventType WIKI_APP_REPLY_NEWS = new WikiAppMessageEventType("message.wikiapp.reply.news");

    public static final WikiAppMessageEventType WIKI_APP_REPLY_COMMENT = new WikiAppMessageEventType("message.wikiapp.reply.comment");

    public static final WikiAppMessageEventType WIKI_APP_COMMENT_USEFUL = new WikiAppMessageEventType("message.wikiapp.reply.comment");

    public static final WikiAppMessageEventType WIKI_APP_DELETE_COMMENT = new WikiAppMessageEventType("message.wikiapp.delete.comment");

    public static final WikiAppMessageEventType WIKI_APP_FEEDBACK_COMMENT = new WikiAppMessageEventType("message.wikiapp.feedback.comment");

    public static final WikiAppMessageEventType WIKI_APP_FEEDBACK_USER = new WikiAppMessageEventType("message.wikiapp.feedback.user");

    public static final WikiAppMessageEventType WIKI_APP_FEEDBACK_GAME = new WikiAppMessageEventType("message.wikiapp.feedback.game");

    public static final WikiAppMessageEventType WIKI_APP_RECOVER_COMMENT = new WikiAppMessageEventType("message.wikiapp.feedback.game");


    public WikiAppMessageEventType(String type) {
        super(type);
    }

    public WikiAppMessageEventType() {
        super();
    }
}
