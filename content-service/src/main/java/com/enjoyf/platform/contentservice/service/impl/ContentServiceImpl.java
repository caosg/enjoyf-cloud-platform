package com.enjoyf.platform.contentservice.service.impl;

import com.enjoyf.platform.common.util.md5.MD5Util;
import com.enjoyf.platform.contentservice.domain.*;
import com.enjoyf.platform.contentservice.domain.contentsum.ContentSum;
import com.enjoyf.platform.contentservice.domain.contentsum.ContentSumType;
import com.enjoyf.platform.contentservice.domain.enumeration.ContentLineOwn;
import com.enjoyf.platform.contentservice.domain.enumeration.ContentLineType;
import com.enjoyf.platform.contentservice.domain.enumeration.ValidStatus;
import com.enjoyf.platform.contentservice.domain.enumeration.WikiappSearchType;
import com.enjoyf.platform.contentservice.event.ContentEventProcess;
import com.enjoyf.platform.contentservice.repository.jpa.ContentLineRepository;
import com.enjoyf.platform.contentservice.repository.jpa.ContentRepository;
import com.enjoyf.platform.contentservice.repository.jpa.ContentTagRepository;
import com.enjoyf.platform.contentservice.repository.redis.AskRedisRepository;
import com.enjoyf.platform.contentservice.repository.redis.AskRedisRepositoryImpl;
import com.enjoyf.platform.contentservice.service.AdvertiseService;
import com.enjoyf.platform.contentservice.service.ContentService;
import com.enjoyf.platform.contentservice.service.ContentSumService;
import com.enjoyf.platform.contentservice.web.rest.util.AskUtil;
import com.enjoyf.platform.contentservice.web.rest.vm.ContentVM;
import com.enjoyf.platform.contentservice.web.rest.vm.ContentGameVM;
import com.enjoyf.platform.event.content.ContentSolrDeleteEvent;
import com.enjoyf.platform.event.content.ContentSolrEvent;
import com.enjoyf.platform.page.ScoreRange;
import com.enjoyf.platform.page.ScoreRangeRows;
import com.google.common.base.Splitter;
import com.hazelcast.util.CollectionUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Service Implementation for managing Content.
 */
@Service
@Transactional
public class ContentServiceImpl implements ContentService {

    private final Logger log = LoggerFactory.getLogger(ContentServiceImpl.class);

    private final ContentRepository contentRepository;


    private final ContentTagRepository contentTagRepository;

    private final AskRedisRepository askRedisRepository;

    private final AdvertiseService advertiseService;

    private final ContentLineRepository contentLineRepository;


    private final ContentSumService contentSumService;

    @Autowired
    private AskUtil askUtil;


    @Autowired
    private ContentEventProcess contentEventProcess;


    public ContentServiceImpl(ContentRepository contentRepository, ContentTagRepository contentTagRepository, AskRedisRepository askRedisRepository, AdvertiseService advertiseService, ContentLineRepository contentLineRepository, ContentSumService contentSumService) {
        this.contentRepository = contentRepository;
        this.contentTagRepository = contentTagRepository;
        this.askRedisRepository = askRedisRepository;
        this.advertiseService = advertiseService;
        this.contentLineRepository = contentLineRepository;
        this.contentSumService = contentSumService;
    }


    /**
     * Save a content.
     *
     * @param content the entity to save
     * @return the persisted entity
     */
    @Override
    public Content save(Content content) {
        log.debug("Request to save Content : {}", content);
        Content result = contentRepository.save(content);
        return result;
    }

    /**
     * Get all the contents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Content> findAll(Pageable pageable) {
        log.debug("Request to get all Contents");
        Page<Content> result = contentRepository.findAll(pageable);
        return result;
    }

    /**
     * Get one content by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Content findOne(Long id) {
        log.debug("Request to get Content : {}", id);
        Content content = askRedisRepository.getContent(id);
        if (content == null) {
            content = contentRepository.findOne(id);
            if (content != null) {
                askRedisRepository.setContent(content);
            }
        }
        return content;
    }

    @Override
    public Content findByCommentId(String commentId) {
        log.debug("Request to get Content : {}", commentId);
        Content content = askRedisRepository.getContentByCommentid(commentId);
        if (content == null) {
            content = contentRepository.findByCommentId(commentId);
            if (content != null) {
                askRedisRepository.setContent(content);
            }
        }
        return content;
    }

    /**
     * Delete the  content by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Content : {}", id);
        contentRepository.delete(id);
    }


    @Override
    public Content postContentWiki(Content content) {
        log.debug("Request to postContentWiki Content : {}", content);
        //wiki提交
        content = save(content);

        askRedisRepository.setContent(content);

        return content;
    }

    @Override
    public Content postContent(Content content) {
        log.debug("Request to postContent Content : {}", content);
        Content oldContent = contentRepository.findByCommentId(content.getCommentId());
        //更新
        if (oldContent != null) {
            processUpdateContent(oldContent, content);
        } else {
            //write to db and redis cache object
            content = save(content);
            askRedisRepository.setContent(content);

            processInsertContent(content);


            ContentSolrEvent insertEvent = new ContentSolrEvent();
            insertEvent.setTitle(content.getTitle());
            insertEvent.setId(content.getId());
            insertEvent.setType(2);
            insertEvent.setCreatetime(content.getPublishTime().getTime());
            insertEvent.setEntryid(AskUtil.getWikiappSearchEntryId(String.valueOf(content.getId()), 2));
            contentEventProcess.send(insertEvent);
        }

        return content;
    }

    @Override
    public boolean updateContentStatus(String commentId, ValidStatus validStatus) {
        log.debug("Request to delete Content : {}", commentId, validStatus);

        Content content = contentRepository.findByCommentId(commentId);
        if (content == null) {
            return false;
        }

        boolean bval = contentRepository.setRemoveStatusById(content.getId(), validStatus.name()) > 0;
        if (bval) {
            String allLineKey = AskUtil.getContentLineKey(ContentLineOwn.ALL_ARCHIVE, "");


            if (validStatus.equals(ValidStatus.VALID)) {
                //更新commnet_line
                contentLineRepository.setValidStatusByLinekeyAndDestId(content.getId(), allLineKey, validStatus.name().toLowerCase());

                //放入redis
                askRedisRepository.setContent(content);

                //加入所有文章下
                askRedisRepository.addContentLine(allLineKey, content.getPublishTime().getTime() + Double.parseDouble("0." + String.valueOf(content.getId())), content.getId());

                ContentSolrEvent insertEvent = new ContentSolrEvent();
                insertEvent.setTitle(content.getTitle());
                insertEvent.setId(content.getId());
                insertEvent.setType(2);
                insertEvent.setCreatetime(content.getPublishTime().getTime());
                insertEvent.setEntryid(AskUtil.getWikiappSearchEntryId(String.valueOf(content.getId()), 2));
                contentEventProcess.send(insertEvent);
            } else {
                //更新commnet_line
                contentLineRepository.setValidStatusByLinekeyAndDestId(content.getId(), allLineKey, validStatus.name().toLowerCase());

                //删除单个对象
                askRedisRepository.delContent(content.getId());

                //删除所有文章下
                askRedisRepository.removeContentLine(allLineKey, content.getId());

                ContentSolrDeleteEvent deleteEvent = new ContentSolrDeleteEvent();
                deleteEvent.setId(content.getId());
                deleteEvent.setType(2);
                contentEventProcess.send(deleteEvent);
            }

        }
        return bval;
    }

    public ScoreRangeRows<Content> findAllContent(ScoreRange scoreRange, Long tagId, String profileId, int pnum) {
        log.debug("Request to delete findAllContent : {}", scoreRange, tagId, profileId, pnum);
        ContentTag contentTag = contentTagRepository.findOne(tagId);

        ScoreRangeRows<Content> returnRows = new ScoreRangeRows<Content>();
        if (contentTag == null) {
            return returnRows;
        }

        Set<String> followGameSet = null;
        if (isToday(scoreRange.getMax())) {
            followGameSet = askRedisRepository.queryFollowGameListByPid(profileId);
        }

        //有游戏
        if (!CollectionUtil.isEmpty(followGameSet)) {

            //取今天的文章
            String todayLineKey = AskUtil.getContentLineKey(ContentLineOwn.TODAY_ALL_ARCHIVE, "");
            Set<String> stringSet = askRedisRepository.zrangeContentLine(todayLineKey);

            //今天没文章，取所有文章
            if (CollectionUtil.isEmpty(stringSet)) {
                return getAll(scoreRange);
            }

            //关注的文章
            List<Content> followContentList = new ArrayList<Content>();

            //未关的文章
            List<Content> unfollowContentList = new ArrayList<Content>();

            //取出用户下游戏集合
            for (String value : stringSet) {
                Content content = findOne(Long.valueOf(value));
                if (content != null) {
                    List<String> contentGameId = Splitter.on(',').splitToList(content.getGameId());
                    boolean exist = false;
                    for (String game : contentGameId) {
                        if (followGameSet.contains(game)) {
                            if (!followContentList.contains(content)) {
                                followContentList.add(content);
                                exist = true;
                            }
                        }
                    }
                    if (!exist) {//未关注文章
                        unfollowContentList.add(content);
                    }
                }
            }

            //关注文章排序
            if (!CollectionUtil.isEmpty(followContentList)) {
                followContentList.sort((Content c1, Content c2) -> c2.getPublishTime().compareTo(c1.getPublishTime()));
            }

            List<Content> todayAllContentList = new ArrayList<Content>();
            todayAllContentList.addAll(followContentList);
            todayAllContentList.addAll(unfollowContentList);

            int start = (pnum - 1) * scoreRange.getSize();
            int end = (pnum - 1) * scoreRange.getSize() + scoreRange.getSize();
            end = todayAllContentList.size() < end ? todayAllContentList.size() : end;

            List<Content> contents = new ArrayList<Content>();
            for (int i = start; i < end; i++) {
                contents.add(todayAllContentList.get(i));
            }

            //所有文章大小
            int sumSize = askRedisRepository.getContentLineSize(AskUtil.getContentLineKey(ContentLineOwn.ALL_ARCHIVE, ""));
            scoreRange.setScoreflag(System.currentTimeMillis());
            scoreRange.setHasnext(sumSize > end ? true : false);
            returnRows = getYesterday(contents, scoreRange);
        } else {
            //直接取所有文章列表
            returnRows = getAll(scoreRange);
        }
        return returnRows;
    }


    @Override
    public ScoreRangeRows<ContentVM> findAllContentVM(String appkey, int platform, ScoreRange scoreRange, Long tagId, String profileId, int pnum) {
        log.debug("Request to get findAllContentVM : {}", scoreRange, tagId, profileId, pnum);

        ScoreRangeRows<Content> page = findAllContent(scoreRange, tagId, profileId, pnum);

        ScoreRangeRows<ContentVM> returnContentVM = new ScoreRangeRows<ContentVM>();
        if (page == null || page.getRows().size() == 0) {
            return returnContentVM;
        }
        returnContentVM.setRange(page.getRange());

        Set<Long> cotentidIdSet = new HashSet<Long>();
        Set<String> commentIdSet = new HashSet<String>();
        Set<String> gameIdSet = new HashSet<String>();
        for (Content content : page.getRows()) {
            commentIdSet.add(content.getCommentId());
            gameIdSet.add(content.getGameId());
        }


        //文章-点赞数
        Map<Long, ContentSum> contentSumMap = contentSumService.queryContentSumByids(cotentidIdSet);
        //文章-游戏名字
        JSONObject gameJson = askUtil.queryGameById(StringUtils.join(gameIdSet, ","));
        //文章-评论数
        Map<String, AskUtil.CommentBeanDTO> commentBeanMap = askUtil.queryCommentbeanById(StringUtils.join(commentIdSet, ","));

        //插入广告
        Map<Integer, Advertise> advertiseMap = new HashMap<>();
        if (pnum == 1) {
            advertiseMap = advertiseService.getAdMap(appkey, platform);
        }

        List<ContentVM> list = new ArrayList<ContentVM>();
        int advIndex = (pnum - 1) * scoreRange.getSize();
        for (int i = 0; i < page.getRows().size(); i++) {
            Content content = page.getRows().get(i);
            list.add(new ContentVM(content, contentSumMap.get(content.getId()), gameJson, commentBeanMap));
            //插入广告
            Advertise advertise = advertiseMap.get(advIndex + i + 1);
            if (advertise != null) {
                list.add(new ContentVM(advertise));
            }
        }
        returnContentVM.setRows(list);

        return returnContentVM;
    }

    @Override
    public Map<Long, Content> queryContentByUserCollect(Set<Long> contentIds) {
        log.debug("Request to queryContentByUserCollect  ids : {}", contentIds);

        Map<Long, Content> map = new HashMap<Long, Content>();
        List<Content> list = contentRepository.findByIdIn(contentIds);
        if (!CollectionUtil.isEmpty(list)) {
            for (Content content : list) {
                map.put(content.getId(), content);
            }
        }
        return map;
    }

    @Override
    public boolean checkContentSum(Long contentId, ContentSumType contentSumType, String profileId) {
        boolean bval = askRedisRepository.checkContentSum(contentId, contentSumType, profileId);
        if (bval) {
            return true;
        }
        return false;
    }


    @Override
    public void hset(String field, String value) {
        log.debug("Request to hset: {}", field, value);
        askRedisRepository.hset(AskRedisRepositoryImpl.JOYMEWIKI_SEARCH_SUGGEST_KEY, field, value);
    }

    @Override
    public void sort(String firsttext, String nexttext) {
        log.debug("Request to sort: {}", firsttext, nexttext);
        String firtValue = askRedisRepository.hget(AskRedisRepositoryImpl.JOYMEWIKI_SEARCH_SUGGEST_KEY, firsttext);
        String otherValue = askRedisRepository.hget(AskRedisRepositoryImpl.JOYMEWIKI_SEARCH_SUGGEST_KEY, nexttext);
        hset(firsttext, otherValue);
        hset(nexttext, firtValue);
    }

    @Override
    public void hdel(String field) {
        log.debug("Request to hdel: {}", field);
        askRedisRepository.hdel(AskRedisRepositoryImpl.JOYMEWIKI_SEARCH_SUGGEST_KEY, field);
    }

    @Override
    public Map getContentSuggestTools() {
        log.debug("Request to getContentSuggestTools");
        return askRedisRepository.hgetAll(AskRedisRepositoryImpl.JOYMEWIKI_SEARCH_SUGGEST_KEY);
    }

    @Override
    public List<String> getContentSuggest() {
        log.debug("Request to getContentSuggest");
        List<String> returnList = new ArrayList<String>();
        Map<Object, Object> objectMap = askRedisRepository.hgetAll(AskRedisRepositoryImpl.JOYMEWIKI_SEARCH_SUGGEST_KEY);
        if (objectMap == null || objectMap.size() == 0) {
            return returnList;
        }

        List<FiledValue> filedValues = new ArrayList<FiledValue>();
        for (Object in : objectMap.keySet()) {
            filedValues.add(new FiledValue(in.toString(), (String) objectMap.get(in)));
        }


        filedValues.sort((FiledValue f1, FiledValue f2) -> Long.valueOf(f2.getValue()).compareTo(Long.valueOf(f1.getValue())));

        for (FiledValue filedValue : filedValues) {
            returnList.add(filedValue.getKey());
        }


        return returnList;
    }


    @Override
    public Page<ContentVM> searchContent(Pageable pageable, String text) {

        Page<ContentVM> pageRows = new PageImpl<ContentVM>(new ArrayList<>(), pageable, 0);

        Page<FiledValue> filedValuePage = askUtil.searchByType(WikiappSearchType.ARCHIVE, pageable, text);

        Set<Long> cotentidIdSet = new HashSet<Long>();
        for (FiledValue filedValue : filedValuePage.getContent()) {
            cotentidIdSet.add(Long.valueOf(filedValue.getKey()));
        }

        Map<Long, Content> contentMap = queryContentByids(cotentidIdSet);


        Set<Long> gameidIdSet = new HashSet<Long>();
        Set<String> commentIdSet = new HashSet<String>();
        for (Long contentId : contentMap.keySet()) {
            commentIdSet.add(contentMap.get(contentId).getCommentId());
            Content content = contentMap.get(contentId);
            if (!StringUtils.isEmpty(content.getGameId())) {
                gameidIdSet.add(Long.valueOf(content.getGameId()));
            }
        }

        //评论数
        Map<String, AskUtil.CommentBeanDTO> commentBeanMap = askUtil.queryCommentbeanById(StringUtils.join(commentIdSet, ","));
        //点赞数
        Map<Long, ContentSum> contentSumMap = contentSumService.queryContentSumByids(cotentidIdSet);
        //文章-游戏名字
        JSONObject gameJson = askUtil.queryGameById(StringUtils.join(gameidIdSet, ","));


        List<ContentVM> list = new ArrayList<ContentVM>();
        for (FiledValue filedValue : filedValuePage.getContent()) {
            Content content = contentMap.get(Long.valueOf(filedValue.getKey()));
            if (content == null) {
                continue;
            }
            list.add(new ContentVM(content, contentSumMap.get(content.getId()), gameJson, commentBeanMap));
        }

        pageRows = new PageImpl<ContentVM>(list, pageable, filedValuePage.getTotalElements());
        return pageRows;
    }

    @Override
    public Page<ContentGameVM> searchGame(Pageable pageable, String text) {

        Page<ContentGameVM> pageRows = new PageImpl<ContentGameVM>(new ArrayList<>(), pageable, 0);

        Page<FiledValue> filedValuePage = askUtil.searchByType(WikiappSearchType.GAME, pageable, text);


        Set<Long> gameIdSet = new HashSet<Long>();
        for (FiledValue filedValue : filedValuePage.getContent()) {
            gameIdSet.add(Long.valueOf(filedValue.getKey()));
        }


        //TODO
//        Map<Long, WikiGameres> wikiGameresMap = AskServiceSngl.get().queryWikiGameresByIds(gameIdSet);
//        List<GameDTO> list = this.buildGameDTOByMap(wikiGameresMap);
//
//        rows.setPage(gameIds.getPage());
//        rows.setRows(list);
        return pageRows;
    }

    @Override
    public Map<Long, Content> queryContentByids(Set<Long> cotentidIdSet) {
        log.debug("Request to get queryContentByids cotentidIdSet: {}", cotentidIdSet);
        Map<Long, Content> map = new HashMap<>();
        for (Long id : cotentidIdSet) {
            Content content = findOne(id);
            if (content != null) {
                map.put(id, content);
            }
        }
        return map;
    }

    private ScoreRangeRows<Content> getYesterday(List<Content> contentList, ScoreRange scoreRange) {
        ScoreRangeRows<Content> returnRows = new ScoreRangeRows<Content>();
        returnRows.setRange(scoreRange);
        returnRows.setRows(contentList);

        //如果数量不足，需要不足剩余部分
        if (contentList.size() < scoreRange.getSize()) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
            scoreRange.setMax(dayLastTime(cal.getTime()).getTime());
            scoreRange.setMin(-1);
            String linekey = AskUtil.getContentLineKey(ContentLineOwn.ALL_ARCHIVE, "");
            Set<String> stringSet = askRedisRepository.queryContentByLineSortRange(linekey, scoreRange);
            for (String value : stringSet) {
                Content content = findOne(Long.valueOf(value));
                if (content != null) {
                    contentList.add(content);
                }
            }
            returnRows.setRange(scoreRange);
            returnRows.setRows(contentList);
        }
        return returnRows;
    }

    private static Date dayLastTime(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    private boolean isToday(double timeStamp) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
        long yesterdayLastTime = dayLastTime(cal.getTime()).getTime();
        if (timeStamp < yesterdayLastTime) {
            return false;
        }
        return true;
    }

    private ScoreRangeRows<Content> getAll(ScoreRange scoreRange) {
        ScoreRangeRows<Content> returnRows = new ScoreRangeRows<Content>();
        List<Content> contentList = new ArrayList<Content>();
        String allLineKey = AskUtil.getContentLineKey(ContentLineOwn.ALL_ARCHIVE, "");
        Set<String> stringSet = askRedisRepository.queryContentByLineSortRange(allLineKey, scoreRange);
        for (String value : stringSet) {
            Content content = findOne(Long.valueOf(value));
            if (content != null) {
                contentList.add(content);
            }
        }
        returnRows.setRange(scoreRange);
        returnRows.setRows(contentList);
        return returnRows;
    }


    private final static LocalDate date = LocalDate.now();

    private void processInsertContent(Content content) {
        //进入所有文章
        String allLineKey = AskUtil.getContentLineKey(ContentLineOwn.ALL_ARCHIVE, "");
        ContentLine contentLine = new ContentLine();
        contentLine.setLinekey(allLineKey);
        contentLine.setOwnId(ContentLineOwn.ALL_ARCHIVE.getCode());
        contentLine.setDestId(content.getId());
        contentLine.setScore(content.getPublishTime().getTime() + Double.parseDouble("0." + String.valueOf(content.getId())));
        contentLine.setCreateTime(new Date());
        contentLine = contentLineRepository.save(contentLine);
        askRedisRepository.addContentLine(allLineKey, contentLine.getScore(), content.getId());

        //进入游戏下文章
        String gameLineKey = AskUtil.getContentLineKey(ContentLineOwn.GAME_ALL_ARCHIVE, content.getGameId());
        ContentLine gameLine = new ContentLine();
        gameLine.setLinekey(gameLineKey);
        gameLine.setOwnId(ContentLineOwn.ALL_ARCHIVE.getCode());
        gameLine.setDestId(content.getId());
        gameLine.setScore(content.getPublishTime().getTime() + Double.parseDouble("0." + String.valueOf(content.getId())));
        gameLine.setCreateTime(new Date());
        contentLineRepository.save(gameLine);
        askRedisRepository.addContentLine(gameLineKey, contentLine.getScore(), content.getId());


        String todayLineKey = MD5Util.Md5(ContentLineOwn.TODAY_ALL_ARCHIVE.getCode() +
            date.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ContentLineType.CONTENTLINE_ARCHIVE.getCode());
        askRedisRepository.addContentLine(todayLineKey, contentLine.getScore(), content.getId());
    }

    private void processUpdateContent(Content oldContent, Content content) {
        content.setId(oldContent.getId());
        askRedisRepository.setContent(content);


        content.setId(oldContent.getId());
        contentRepository.save(content);


        ContentSolrEvent insertEvent = new ContentSolrEvent();
        insertEvent.setTitle(content.getTitle());
        insertEvent.setId(content.getId());
        insertEvent.setType(2);
        insertEvent.setCreatetime(content.getPublishTime().getTime());
        insertEvent.setEntryid(AskUtil.getWikiappSearchEntryId(String.valueOf(content.getId()), 2));
        contentEventProcess.send(insertEvent);


        //修改了发布时间
        if (content.getPublishTime().getTime() != oldContent.getPublishTime().getTime()) {
            double score = content.getPublishTime().getTime() + Double.parseDouble("0." + String.valueOf(oldContent.getId()));
            //进入所有文章
            String allLineKey = AskUtil.getContentLineKey(ContentLineOwn.ALL_ARCHIVE, "");
            contentLineRepository.setScoreByLinekeyAndDestId(oldContent.getId(), allLineKey, score);
            askRedisRepository.addContentLine(allLineKey, score, oldContent.getId());


            //进入游戏下文章
            String gameLineKey = AskUtil.getContentLineKey(ContentLineOwn.GAME_ALL_ARCHIVE, content.getGameId());
            contentLineRepository.setScoreByLinekeyAndDestId(oldContent.getId(), gameLineKey, score);
            askRedisRepository.addContentLine(gameLineKey, score, oldContent.getId());


            //修改文章对应的
            String todayLineKey = MD5Util.Md5(ContentLineOwn.TODAY_ALL_ARCHIVE.getCode() +
                date.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ContentLineType.CONTENTLINE_ARCHIVE.getCode());

            String oldLineKey = MD5Util.Md5(ContentLineOwn.TODAY_ALL_ARCHIVE.getCode() +
                date.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ContentLineType.CONTENTLINE_ARCHIVE.getCode());

            askRedisRepository.addContentLine(todayLineKey, score, oldContent.getId());

            if (!todayLineKey.equals(oldLineKey)) {
                askRedisRepository.removeContentLine(oldLineKey, oldContent.getId());
            }

        }
    }

}
