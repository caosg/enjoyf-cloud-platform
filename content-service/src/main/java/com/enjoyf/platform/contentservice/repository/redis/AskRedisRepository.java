package com.enjoyf.platform.contentservice.repository.redis;

import com.enjoyf.platform.contentservice.domain.Advertise;
import com.enjoyf.platform.contentservice.domain.Content;
import com.enjoyf.platform.contentservice.domain.ContentTag;
import com.enjoyf.platform.contentservice.domain.UserCollect;
import com.enjoyf.platform.contentservice.domain.contentsum.ContentSum;
import com.enjoyf.platform.contentservice.domain.contentsum.ContentSumType;
import com.enjoyf.platform.contentservice.domain.enumeration.ContentTagLine;
import com.enjoyf.platform.page.Pagination;
import com.enjoyf.platform.page.ScoreRange;

import java.util.Map;
import java.util.Set;

/**
 * Created by zhimingli on 2017/5/12.
 */
public interface AskRedisRepository {
    void delContentTag(long tagid);

    void setContentTag(ContentTag contentTag);

    ContentTag getContentTag(long tagid);


    void addContentTag(String tagLine, ContentTag contentTag);

    boolean removeContentTag(String tagLine, Long tagid);

    Set<String> zrangeContentTag(String tagLine);


    void setContent(Content content);

    Content getContent(long contentid);

    Content getContentByCommentid(String commentId);

    boolean delContent(long contentid);


    void addContentLine(String linekey, double score, Long destId);

    int getContentLineSize(String linekey);

    boolean removeContentLine(String lineKey, Long contenId);


    Set<String> queryContentByLineSortRange(String lineKey, ScoreRange scoreRange);

    Set<String> zrangeContentLine(String lineKey);


    //返回人关注的所有游戏
    Set<String> queryFollowGameListByPid(String pid);


    //广告
    void addAdvertiseLine(String lineKey, Advertise advertise);

    boolean removeAdvertiseLine(String lineKey, Long advertiseId);


    Set<String> zrangeAdvertiseLine(String lineKey);


    boolean delAdvertise(long advertiseId);

    void setAdvertise(Advertise advertise);

    Advertise getAdvertise(long advertiseId);

    ////////////////////////////////////////////收藏相关/////////////////////////////////////////////////////////////////
    //新增收藏
    void addUserCollectList(UserCollect userCollect);

    boolean removeUserCollectList(String profileId, String s);

    ///////////////////////////收藏的实体
    void delUserCollect(long id);

    void setUserCollect(UserCollect userCollect);

    UserCollect getUserCollect(long id);

    Set<String> queryUserCollectList(String profileId, Pagination pagination, boolean b);

    long getUserCollectNum(String pid);

    boolean checkContentSum(Long contentId, ContentSumType agreeNum, String profileId);


    boolean increaseContentSum(Long contentId, ContentSumType agreeNum, int i, String profileId);

    ContentSum getContentSumById(long contentId);


    //搜索
    Map<Object, Object> hgetAll(String key);

    void hset(String key, String field, String value);

    String hget(String key, String field);

    Long hdel(String key, String field);
}
