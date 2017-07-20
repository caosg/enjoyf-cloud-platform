package com.enjoyf.platform.event.content;

import com.enjoyf.platform.event.Event;

/**
 * Created by zhimingli on 2017/5/31.
 */

public class ContentSolrEvent extends Event {

    public ContentSolrEvent() {
        super(ContentEventType.CONTENT_INSERT, ContentEventConstants.bindKey);
    }

    private String entryid;//唯一(id+type) as AskUtil.getWikiappSearchEntryId()
    private Long id; //文章或者游戏的ID
    private int type = 1; //1--game 2-archive
    private String title;
    private long createtime;


    public String getEntryid() {
        return entryid;
    }

    public void setEntryid(String entryid) {
        this.entryid = entryid;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }


}
