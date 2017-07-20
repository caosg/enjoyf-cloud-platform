package com.enjoyf.platform.contentservice.web.rest.vm;

import com.enjoyf.platform.contentservice.domain.ContentTag;
import com.enjoyf.platform.contentservice.domain.enumeration.ContentTagType;

/**
 * Created by zhimingli on 2017/5/12.
 * 标签列表
 */
public class ContentTagVM {
    private String jt;
    private String ji;
    private String tagname;

    public ContentTagVM(ContentTag tag) {
        this.tagname = tag.getName();
        this.jt = String.valueOf(tag.getTagType());
        if (tag.getTagType() == ContentTagType.ARCHIVE.getCode()) {
            this.ji = String.valueOf(tag.getId());
        } else {
            this.ji = tag.getTarget();
        }
    }

    public String getJt() {
        return jt;
    }

    public void setJt(String jt) {
        this.jt = jt;
    }

    public String getJi() {
        return ji;
    }

    public void setJi(String ji) {
        this.ji = ji;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    @Override
    public String toString() {
        return "ContentTagVM{" +
            "jt='" + jt + '\'' +
            ", ji='" + ji + '\'' +
            ", tagname='" + tagname + '\'' +
            '}';
    }
}

