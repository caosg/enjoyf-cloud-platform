package com.enjoyf.platform.event.content;

import com.enjoyf.platform.event.Event;

/**
 * Created by zhimingli on 2017/5/31.
 */

public class ContentSolrDeleteEvent extends Event {

    public ContentSolrDeleteEvent() {
        super(ContentEventType.CONTENT_INSERT, ContentEventConstants.bindKey);
    }

    private Long id;//唯一(id+type) as AskUtil.getWikiappSearchEntryId()
    private int type = 2;//1--game 2-archive


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
