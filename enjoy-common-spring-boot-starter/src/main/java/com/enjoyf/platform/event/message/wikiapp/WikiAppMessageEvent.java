package com.enjoyf.platform.event.message.wikiapp;

import com.enjoyf.platform.event.Event;
import com.enjoyf.platform.event.EventType;

/**
 * Created by ericliu on 2017/6/19.
 */
public abstract class WikiAppMessageEvent extends Event {

    public WikiAppMessageEvent(EventType eventType, String bindKey) {
        super(eventType, bindKey);
    }

}
