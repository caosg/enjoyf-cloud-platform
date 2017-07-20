package com.enjoyf.platform.contentservice.repository.redis;

import com.enjoyf.platform.contentservice.domain.Advertise;
import com.enjoyf.platform.contentservice.domain.Content;
import com.enjoyf.platform.contentservice.domain.ContentTag;
import com.enjoyf.platform.contentservice.domain.UserCollect;
import com.enjoyf.platform.contentservice.domain.contentsum.ContentSum;
import com.enjoyf.platform.contentservice.domain.contentsum.ContentSumType;
import com.enjoyf.platform.page.Pagination;
import com.enjoyf.platform.page.ScoreRange;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * Created by zhimingli on 2017/5/12.
 */
@Component
public class AskRedisRepositoryImpl extends AbstractRedis implements AskRedisRepository {

    private static final String PREFIX = "askservice";

    //新的key
    private static final String PREFIX_NEW = "askservice_micro";

    //标签obj
    private static final String KEY_CONTENTTAG_OBJ = PREFIX_NEW + "_tagobj_";

    //标签line
    private static final String KEY_CONTENTTAG_LINE = PREFIX_NEW + "_tagline_";

    private static final String KEY_CONTEN_OBJ = PREFIX_NEW + "_contentobj_";

    private static final String KEY_CONTEN_COMMENTID = PREFIX_NEW + "_contentcommentid_";


    private static final String KEY_CONTEN_LINEKEY = PREFIX + "_contentlinekey_";


    private static final String KEY_USER_FOLLOW_GAMES = PREFIX + "_user_follow_game_list_";


    //广告obj
    private static final String KEY_ADVERTISE_OBJ = PREFIX_NEW + "_adobj_";

    //广告line
    private static final String KEY_ADVERTISE_LINE = PREFIX + "_adline_";

    private static final String KEY_USER_COLLECT_LIST = PREFIX + "_user_collect_list_";

    private static final String KEY_USER_COLLECT = PREFIX + "_user_collect_";

    private static final String KEY_CONTENSUM_LINE = PREFIX + "_contensumline_";

    private static final String KEY_CONTENSUM_OBJ = PREFIX + "_contensumobj_";


    public static String JOYMEWIKI_SEARCH_SUGGEST_KEY = "askserver_joymewiki_search_suggest_key";


    private StringRedisTemplate redisTemplate;

    public AskRedisRepositoryImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public void delContentTag(long tagid) {
        redisTemplate.delete(KEY_CONTENTTAG_OBJ + tagid);
    }

    public void setContentTag(ContentTag contentTag) {
        redisTemplate.opsForValue().set(KEY_CONTENTTAG_OBJ + contentTag.getId(), new Gson().toJson(contentTag));
    }

    public ContentTag getContentTag(long tagid) {
        String objStr = redisTemplate.opsForValue().get(KEY_CONTENTTAG_OBJ + tagid);
        if (StringUtils.isEmpty(objStr)) {
            return null;
        }
        return new Gson().fromJson(objStr, ContentTag.class);
    }

    public void addContentTag(String tagLine, ContentTag contentTag) {
        redisTemplate.opsForZSet().add(KEY_CONTENTTAG_LINE + tagLine, String.valueOf(contentTag.getId()), contentTag.getDisplayOrder());
    }

    public boolean removeContentTag(String tagLine, Long tagid) {
        redisTemplate.opsForZSet().remove(KEY_CONTENTTAG_LINE + tagLine, String.valueOf(tagid));
        return true;
    }

    public Set<String> zrangeContentTag(String tagLine) {
        Set<String> stringSet = redisTemplate.opsForZSet().reverseRangeByScore(KEY_CONTENTTAG_LINE + tagLine, 0L, System.currentTimeMillis());
        return stringSet;
    }


    public void setContent(Content content) {
        redisTemplate.opsForValue().set(KEY_CONTEN_OBJ + content.getId(), new Gson().toJson(content));
        redisTemplate.opsForValue().set(KEY_CONTEN_COMMENTID + content.getCommentId(), new Gson().toJson(content));
    }

    public Content getContent(long contentid) {
        String objStr = redisTemplate.opsForValue().get(KEY_CONTEN_OBJ + contentid);
        if (StringUtils.isEmpty(objStr)) {
            return null;
        }
        return new Gson().fromJson(objStr, Content.class);
    }

    public Content getContentByCommentid(String commentId) {
        String objStr = redisTemplate.opsForValue().get(KEY_CONTEN_COMMENTID + commentId);
        if (StringUtils.isEmpty(objStr)) {
            return null;
        }
        return new Gson().fromJson(objStr, Content.class);
    }

    public boolean delContent(long contentid) {
        Content content = getContent(contentid);
        if (content == null) {
            return false;
        }
        redisTemplate.delete(KEY_CONTEN_COMMENTID + content.getCommentId());
        redisTemplate.delete(KEY_CONTEN_OBJ + contentid);
        return true;
    }


    public void addContentLine(String linekey, double score, Long destId) {
        //redisTemplate.zadd(KEY_CONTEN_LINEKEY + linekey, score, String.valueOf(destId), -1);
        redisTemplate.opsForZSet().add(KEY_CONTEN_LINEKEY + linekey, String.valueOf(destId), score);
    }

    public int getContentLineSize(String linekey) {
        return redisTemplate.opsForZSet().zCard(KEY_CONTEN_LINEKEY + linekey).intValue();
    }

    public boolean removeContentLine(String lineKey, Long contenId) {
        return redisTemplate.opsForZSet().remove(KEY_CONTEN_LINEKEY + lineKey, String.valueOf(contenId)) > 0;
    }


    public Set<String> queryContentByLineSortRange(String lineKey, ScoreRange scoreRange) {
        Set<String> stringSet = zrangeByScore(redisTemplate, KEY_CONTEN_LINEKEY + lineKey, scoreRange);
        return stringSet;
    }

    public Set<String> zrangeContentLine(String lineKey) {
        Set<String> stringSet = redisTemplate.opsForZSet().range(KEY_CONTEN_LINEKEY + lineKey, 0L, -1L);
        return stringSet;
    }

    public Set<String> queryFollowGameListByPid(String pid) {
        Set<String> stringSet = redisTemplate.opsForZSet().range(KEY_USER_FOLLOW_GAMES + pid, 0, -1);
        return stringSet;
    }


    public void addAdvertiseLine(String lineKey, Advertise advertise) {
        redisTemplate.opsForZSet().add(KEY_ADVERTISE_LINE + lineKey, String.valueOf(advertise.getId()), advertise.getDisplayOrder());
    }

    public boolean removeAdvertiseLine(String lineKey, Long advertiseId) {
        return redisTemplate.opsForZSet().remove(KEY_ADVERTISE_LINE + lineKey, String.valueOf(advertiseId)) > 0;
    }

    public Set<String> zrangeAdvertiseLine(String lineKey) {
        return redisTemplate.opsForZSet().range(KEY_ADVERTISE_LINE + lineKey, 0L, Long.MAX_VALUE);
    }


    public boolean delAdvertise(long advertiseId) {
        redisTemplate.delete(KEY_ADVERTISE_OBJ + advertiseId);
        return true;
    }

    public void setAdvertise(Advertise advertise) {
        redisTemplate.opsForValue().set(KEY_ADVERTISE_OBJ + advertise.getId(), new Gson().toJson(advertise));
    }

    public Advertise getAdvertise(long advertiseId) {
        String objStr = redisTemplate.opsForValue().get(KEY_ADVERTISE_OBJ + advertiseId);
        if (StringUtils.isEmpty(objStr)) {
            return null;
        }
        return new Gson().fromJson(objStr, Advertise.class);
    }

    //新增收藏
    @Override
    public void addUserCollectList(UserCollect userCollect) {
        redisTemplate.opsForZSet().add(KEY_USER_COLLECT_LIST + userCollect.getProfileId(), String.valueOf(userCollect.getId()), System.currentTimeMillis());
    }

    @Override
    public boolean removeUserCollectList(String profileId, String s) {
        return redisTemplate.opsForZSet().remove(KEY_USER_COLLECT_LIST + profileId, s) > 0;
    }

    @Override
    public void delUserCollect(long id) {
        redisTemplate.delete(KEY_USER_COLLECT + id);
    }

    @Override
    public void setUserCollect(UserCollect userCollect) {
        redisTemplate.opsForValue().set(KEY_USER_COLLECT + userCollect.getId(), new Gson().toJson(userCollect));
    }

    @Override
    public UserCollect getUserCollect(long id) {
        String objStr = redisTemplate.opsForValue().get(KEY_USER_COLLECT + id);

        if (StringUtils.isEmpty(objStr)) {
            return null;
        }
        return new Gson().fromJson(objStr, UserCollect.class);
    }

    @Override
    public Set<String> queryUserCollectList(String profileId, Pagination pagination, boolean desc) {
        Set<String> stringSet = null;
        if (desc) {
            stringSet = redisTemplate.opsForZSet().reverseRange(KEY_USER_COLLECT_LIST + profileId, pagination.getStartRowIdx(), pagination.getEndRowIdx());
        } else {
            stringSet = redisTemplate.opsForZSet().range(KEY_USER_COLLECT_LIST + profileId, pagination.getStartRowIdx(), pagination.getEndRowIdx());
        }
        return stringSet;
    }

    public long getUserCollectNum(String pid) {
        return redisTemplate.opsForZSet().zCard(KEY_USER_COLLECT_LIST + pid);
    }

    @Override
    public boolean checkContentSum(Long contentId, ContentSumType contentSumType, String profileId) {
        Double result = redisTemplate.opsForZSet().score(KEY_CONTENSUM_LINE + contentId + contentSumType.getCode(), profileId);
        if (result == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean increaseContentSum(Long contentId, ContentSumType contentSumType, int i, String profileId) {
        boolean bval = redisTemplate.opsForHash().increment(KEY_CONTENSUM_OBJ + contentId, contentSumType.getCode(), i) > 0;

        //文章对应的人的列表
        redisTemplate.opsForZSet().add(KEY_CONTENSUM_LINE + contentId + contentSumType.getCode(), profileId, System.currentTimeMillis());
        return bval;
    }

    private int getContentSumValue(long contentId, ContentSumType field) {
        Object val = redisTemplate.opsForHash().get(KEY_CONTENSUM_OBJ + contentId, field.getCode());
        if (val == null) {
            return 0;
        }
        return Integer.parseInt(val.toString());
    }

    public ContentSum getContentSumById(long contentId) {
        ContentSum contentSum = new ContentSum();
        contentSum.setId(contentId);
        contentSum.setAgree_num(getContentSumValue(contentId, ContentSumType.AGREE_NUM));
        return contentSum;
    }

    @Override
    public Map<Object, Object> hgetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public void hset(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    @Override
    public String hget(String key, String field) {
        return (String) redisTemplate.opsForHash().get(key, field);
    }

    @Override
    public Long hdel(String key, String field) {
        return redisTemplate.opsForHash().delete(key, field);
    }
}
