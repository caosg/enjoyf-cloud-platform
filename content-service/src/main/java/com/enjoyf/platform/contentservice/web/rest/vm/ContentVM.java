package com.enjoyf.platform.contentservice.web.rest.vm;

import com.enjoyf.platform.contentservice.domain.Advertise;
import com.enjoyf.platform.contentservice.domain.Content;
import com.enjoyf.platform.contentservice.domain.contentsum.ContentSum;
import com.enjoyf.platform.contentservice.web.rest.util.AskUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created by zhimingli on 2017/5/12.
 * 标签列表详情页
 */
public class ContentVM {
    private long contentid = 0;
    private int type = 1;
    private String picurl;
    private String title;
    private String desc;
    private int praisenum = 0;//点赞
    private int replynum = 0;//评论
    private long time;
    private String jt;
    private String ji;
    private String gamename;

    public long getContentid() {
        return contentid;
    }

    public void setContentid(long contentid) {
        this.contentid = contentid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPraisenum() {
        return praisenum;
    }

    public void setPraisenum(int praisenum) {
        this.praisenum = praisenum;
    }

    public int getReplynum() {
        return replynum;
    }

    public void setReplynum(int replynum) {
        this.replynum = replynum;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
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

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }


    public ContentVM(Content content, ContentSum contentSum, JSONObject gameJson, Map<String, AskUtil.CommentBeanDTO> commentBeanMap) {
        this.setContentid(content.getId());
        this.setPicurl(StringUtils.isEmpty(content.getPic()) ? "" : content.getPic());
        this.setTitle(StringUtils.isEmpty(content.getTitle()) ? "" : content.getTitle());
        this.setDesc(StringUtils.isEmpty(content.getDescription()) ? "" : content.getDescription());
        this.setTime(content.getPublishTime().getTime());
        this.setJi(StringUtils.isEmpty(content.getWebUrl()) ? "" : content.getWebUrl());
        this.setJt("-2");

        String gameName = gameJson.get(content.getGameId()) == null ? "" : gameJson.getString(content.getGameId());
        AskUtil.CommentBeanDTO bean = commentBeanMap.get(content.getCommentId());
        if (bean != null) {
            this.setReplynum(bean.getTotalRows() < 0 ? 0 : bean.getTotalRows());
        }

        if (contentSum != null) {
            this.setPraisenum(contentSum.getAgree_num() < 0 ? 0 : contentSum.getAgree_num());
        }
        this.setGamename(StringUtils.isEmpty(gameName) ? "" : gameName);
    }


    public ContentVM(Advertise advertise) {
        this.setPicurl(StringUtils.isEmpty(advertise.getPic()) ? "" : advertise.getPic());
        this.setTitle(StringUtils.isEmpty(advertise.getTitle()) ? "" : advertise.getTitle());
        this.setDesc(StringUtils.isEmpty(advertise.getDescription()) ? "" : advertise.getDescription());
        this.setTime(advertise.getCreateDate().getTime());
        if (advertise.getType() == -3) {
            String target = advertise.getTarget();
            this.setJi(StringUtils.isEmpty(target) ? "" : target);
            this.setJt("-2");
        } else {
            this.setJi(StringUtils.isEmpty(advertise.getTarget()) ? "" : advertise.getTarget());
            this.setJt(String.valueOf(advertise.getType()));
        }
        this.type = 2;
    }
}
