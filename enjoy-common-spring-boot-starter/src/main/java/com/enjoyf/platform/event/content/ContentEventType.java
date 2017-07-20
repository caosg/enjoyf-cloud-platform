package com.enjoyf.platform.event.content;

import com.enjoyf.platform.event.EventType;

/**
 * Created by ericliu on 2017/5/10.
 */
public class ContentEventType extends EventType {

    public static final ContentEventType CONTENT_INSERT = new ContentEventType("content.insert");

    public ContentEventType(String type) {
        super(type);
    }

    public ContentEventType() {
        super();
    }
}
