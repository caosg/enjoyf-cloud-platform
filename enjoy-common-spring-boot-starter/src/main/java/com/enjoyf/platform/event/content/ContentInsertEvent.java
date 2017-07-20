package com.enjoyf.platform.event.content;

import com.enjoyf.platform.event.Event;
import com.enjoyf.platform.event.user.UserEventConstants;

/**
 * Created by ericliu on 2017/5/10.
 */
@Deprecated
public class ContentInsertEvent extends Event {


    public ContentInsertEvent() {
        super(ContentEventType.CONTENT_INSERT, ContentEventConstants.bindKey);
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ContentInsertEvent{" +
                "title='" + title + '\'' +
                '}';
    }
}
