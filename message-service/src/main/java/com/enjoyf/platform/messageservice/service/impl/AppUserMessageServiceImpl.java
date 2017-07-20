package com.enjoyf.platform.messageservice.service.impl;

import com.enjoyf.platform.autoconfigure.web.error.BusinessException;
import com.enjoyf.platform.common.domain.enumeration.ValidStatus;
import com.enjoyf.platform.messageservice.config.ApplicationProperties;
import com.enjoyf.platform.messageservice.domain.AppUserMessage;
import com.enjoyf.platform.messageservice.domain.AppUserMessageSum;
import com.enjoyf.platform.messageservice.domain.enumration.AppMessageType;
import com.enjoyf.platform.messageservice.repository.jpa.AppUserMessageRepository;
import com.enjoyf.platform.messageservice.repository.redis.RedisAppUserMessageRepository;
import com.enjoyf.platform.messageservice.repository.redis.RedisAppUserMessageSetRepository;
import com.enjoyf.platform.messageservice.repository.redis.RedisAppUserMessageSumRepository;
import com.enjoyf.platform.messageservice.service.AppUserMessageService;
import com.enjoyf.platform.messageservice.service.contentservice.GameFeignClient;
import com.enjoyf.platform.messageservice.service.contentservice.domain.Game;
import com.enjoyf.platform.messageservice.service.dto.AppUserMessageDTO;
import com.enjoyf.platform.messageservice.service.dto.wikiapp.*;
import com.enjoyf.platform.messageservice.service.mapper.AppUserMessageMapper;
import com.enjoyf.platform.messageservice.service.userservice.UserProfileFeignClient;
import com.enjoyf.platform.messageservice.service.userservice.domain.UserProfileDTO;
import com.enjoyf.platform.page.ScoreRange;
import com.enjoyf.platform.page.ScoreRangeRows;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Service Implementation for managing AppUserMessage.
 */
@Service
public class AppUserMessageServiceImpl implements AppUserMessageService {

    private final Logger log = LoggerFactory.getLogger(AppUserMessageServiceImpl.class);

    private final AppUserMessageRepository appUserMessageRepository;
    private final RedisAppUserMessageRepository redisAppUserMessageRepository;
    private final RedisAppUserMessageSetRepository redisAppUserMessageSetRepository;
    private final RedisAppUserMessageSumRepository redisAppUserMessageSumRepository;

    private final ObjectMapper objectMapper;

    private final UserProfileFeignClient userProfileFeignClient;
    private final GameFeignClient gameFeignClient;

    private final MessageSource messageSource;

    @Autowired
    private ApplicationProperties applicationProperties;

    public AppUserMessageServiceImpl(AppUserMessageRepository appUserMessageRepository,
                                     RedisAppUserMessageRepository redisAppUserMessageRepository,
                                     RedisAppUserMessageSetRepository redisAppUserMessageSetRepository,
                                     RedisAppUserMessageSumRepository redisAppUserMessageSumRepository,

                                     UserProfileFeignClient userProfileFeignClient,
                                     GameFeignClient gameFeignClient,

                                     ObjectMapper objectMapper,
                                     MessageSource messageSource) {
        this.appUserMessageRepository = appUserMessageRepository;
        this.redisAppUserMessageRepository = redisAppUserMessageRepository;
        this.redisAppUserMessageSumRepository = redisAppUserMessageSumRepository;
        this.redisAppUserMessageSetRepository = redisAppUserMessageSetRepository;
        this.objectMapper = objectMapper;
        this.userProfileFeignClient = userProfileFeignClient;
        this.gameFeignClient = gameFeignClient;

        this.messageSource = messageSource;
    }

    /**
     * Save a appUserMessage.
     *
     * @param appUserMessage the entity to save
     * @return the persisted entity
     */
    @Override
    public AppUserMessage save(AppUserMessage appUserMessage) {
        log.debug("Request to save AppUserMessage : {}", appUserMessage);
        AppUserMessage result = appUserMessageRepository.save(appUserMessage);

        redisAppUserMessageRepository.save(appUserMessage);

        redisAppUserMessageSetRepository.saddMessageApp(appUserMessage);

        redisAppUserMessageSumRepository.increaseMessageSum(appUserMessage.getAppkey(), appUserMessage.getUid(), 1);

        return result;
    }


    @Override
    public ScoreRangeRows<AppUserMessageDTO> findByUidAppkey(long uid, String appKey, ScoreRange scoreRange) {
        List<Long> messageIdSet = redisAppUserMessageSetRepository.findMessageIds(appKey, uid, scoreRange);
        Set<AppUserMessage> appUserMessageSet = new LinkedHashSet<>();
        messageIdSet.stream().forEach(messageId -> {
            AppUserMessage userMessage = findOne(messageId);
            if (userMessage != null) {
                appUserMessageSet.add(userMessage);
            }
        });

        List<AppUserMessageDTO> list = new ArrayList<>();
        if (appKey.equals(applicationProperties.getWikiappkey())) {
            list = buildWikiAppUserMessageDTO(appUserMessageSet);
        }

        ScoreRangeRows<AppUserMessageDTO> result = new ScoreRangeRows<AppUserMessageDTO>();
        result.setRows(list);
        result.setRange(scoreRange);

        return result;
    }

    public List<AppUserMessageDTO> buildWikiAppUserMessageDTO(Set<AppUserMessage> appUserMessageSet) {
        List<AppUserMessageDTO> dtoList = new ArrayList<>();

        Map<Long, UserProfileDTO> userMap = new HashMap<>();

        Set<Long> uids = new HashSet<>();
        Set<Long> gameIds = new HashSet<>();
        for (AppUserMessage appUserMessage : appUserMessageSet) {
            AppUserMessageDTO dto = AppUserMessageMapper.MAPPER.toDTO(appUserMessage);
            try {
                if (dto.getMessageType().equals(AppMessageType.replynews)) {
                    ReplyNewsMessageBody body = objectMapper.readValue(appUserMessage.getMessageBody(), ReplyNewsMessageBody.class);
                    dto.setMessageBodyObj(body);
                    uids.add(body.getReplyUid());
                } else if (dto.getMessageType().equals(AppMessageType.replycomment)) {
                    ReplyCommentMessageBody body = objectMapper.readValue(appUserMessage.getMessageBody(), ReplyCommentMessageBody.class);
                    dto.setMessageBodyObj(body);
                    gameIds.add(body.getGameId());
                    uids.add(body.getReplyUid());
                } else if (dto.getMessageType().equals(AppMessageType.commentuseful)) {
                    CommentUsefulMessageBody body = objectMapper.readValue(appUserMessage.getMessageBody(), CommentUsefulMessageBody.class);
                    dto.setMessageBodyObj(body);
                    gameIds.add(body.getGameId());
                    uids.add(body.getDestUid());
                } else if (dto.getMessageType().equals(AppMessageType.feedbackgame)) {
                    FeedbackGameMessageBody body = objectMapper.readValue(appUserMessage.getMessageBody(), FeedbackGameMessageBody.class);
                    dto.setMessageBodyObj(body);
                    gameIds.add(body.getGameId());
                } else if (dto.getMessageType().equals(AppMessageType.feedbackuser)) {
                    FeedbackUserMessageBody body = objectMapper.readValue(appUserMessage.getMessageBody(), FeedbackUserMessageBody.class);
                    dto.setMessageBodyObj(body);
                    uids.add(body.getFeedbackUid());
                } else if (dto.getMessageType().equals(AppMessageType.feedbackcomment)) {
                    FeedbackCommentMessageBody body = objectMapper.readValue(appUserMessage.getMessageBody(), FeedbackCommentMessageBody.class);
                    dto.setMessageBodyObj(body);
                    uids.add(body.getFeedbackUid());
                    gameIds.add(body.getGameId());
                } else if (dto.getMessageType().equals(AppMessageType.deletecomment)) {
                    DeleteCommentMessageBody body = objectMapper.readValue(appUserMessage.getMessageBody(), DeleteCommentMessageBody.class);
                    dto.setMessageBodyObj(body);
//                    uids.add(body.getCommentUid());
//                    if (body.getGameId() != null) {
//                        gameIds.add(body.getGameId());
//                    }
                } else if (dto.getMessageType().equals(AppMessageType.recovercomment)) {
                    RecoverCommentMessageBody body = objectMapper.readValue(appUserMessage.getMessageBody(), RecoverCommentMessageBody.class);
                    dto.setMessageBodyObj(body);
                }
            } catch (IOException e) {
                log.error("build wikiapp messagebody error.", e);
                continue;
            }

            dtoList.add(dto);
        }

        Long[] uidArray = uids.toArray(new Long[]{});
        if (!ArrayUtils.isEmpty(uidArray)) {
            List<UserProfileDTO> userProfileDTOS = userProfileFeignClient.findUserProfilesByUids(uidArray);
            userProfileDTOS.stream().forEach(userProfileDTO -> {
                userMap.put(userProfileDTO.getId(), userProfileDTO);
            });
        }

        Long[] gameIdArray = gameIds.toArray(new Long[]{});
        Map<Long, Game> gameMap = new HashMap<>();
        if (!ArrayUtils.isEmpty(gameIdArray)) {
            gameMap.putAll(gameFeignClient.findByIds(gameIdArray));
        }

//有人回复了你在｛新闻标题｝的评论，点击查看.
//有人回复了你对｛游戏名称｝的点评，点击查看.
//{用户名}觉得你在{游戏名称}的点评非常有用。
        dtoList.stream().forEach(dto -> {
            String bodyText = "";
            UserProfileDTO destUser = null;
            if (dto.getMessageType().equals(AppMessageType.replynews)) {
                ReplyNewsMessageBody body = (ReplyNewsMessageBody) dto.getMessageBodyObj();
                destUser = userMap.get(body.getReplyUid());
                bodyText = messageSource.getMessage("wikiapp.messagetemplate." + dto.getMessageType().name(), new Object[]{destUser.getNick(), body.getTitle()}, Locale.CHINESE);
            } else if (dto.getMessageType().equals(AppMessageType.replycomment)) {
                ReplyCommentMessageBody body = (ReplyCommentMessageBody) dto.getMessageBodyObj();
                Game game = gameMap.get(body.getGameId());
                destUser = userMap.get(body.getReplyUid());
                bodyText = messageSource.getMessage("wikiapp.messagetemplate." + dto.getMessageType().name(), new Object[]{destUser.getNick(), game.getName()}, Locale.CHINESE);
            } else if (dto.getMessageType().equals(AppMessageType.commentuseful)) {
                CommentUsefulMessageBody body = (CommentUsefulMessageBody) dto.getMessageBodyObj();
                destUser = userMap.get(body.getDestUid());
                Game game = gameMap.get(body.getGameId());
                bodyText = messageSource.getMessage("wikiapp.messagetemplate." + dto.getMessageType().name(), new Object[]{destUser.getNick(), game.getName()}, Locale.CHINESE);
            } else if (dto.getMessageType().equals(AppMessageType.feedbackgame)) {
                FeedbackGameMessageBody body = (FeedbackGameMessageBody) dto.getMessageBodyObj();
                Game game = gameMap.get(body.getGameId());
                bodyText = messageSource.getMessage("wikiapp.messagetemplate." + dto.getMessageType().name(), new Object[]{game.getName(), body.getBody()}, Locale.CHINESE);
            } else if (dto.getMessageType().equals(AppMessageType.feedbackuser)) {
                FeedbackUserMessageBody body = (FeedbackUserMessageBody) dto.getMessageBodyObj();
                UserProfileDTO feedbackUser = userMap.get(body.getFeedbackUid());
                bodyText = messageSource.getMessage("wikiapp.messagetemplate." + dto.getMessageType().name(), new Object[]{feedbackUser.getNick(), body.getBody()}, Locale.CHINESE);
            } else if (dto.getMessageType().equals(AppMessageType.feedbackcomment)) {
                FeedbackCommentMessageBody body = (FeedbackCommentMessageBody) dto.getMessageBodyObj();
                Game game = gameMap.get(body.getGameId());
                UserProfileDTO feedbackUser = userMap.get(body.getFeedbackUid());

                bodyText = messageSource.getMessage("wikiapp.messagetemplate." + dto.getMessageType().name(), new Object[]{feedbackUser.getNick(), game.getName(), body.getBody()}, Locale.CHINESE);
            } else if (dto.getMessageType().equals(AppMessageType.deletecomment)) {
                DeleteCommentMessageBody body = (DeleteCommentMessageBody) dto.getMessageBodyObj();
                Game game = gameMap.get(body.getGameId());
                bodyText = messageSource.getMessage("wikiapp.messagetemplate." + dto.getMessageType().name(), new Object[]{body.getReason(), game.getName()}, Locale.CHINESE);
            } else if (dto.getMessageType().equals(AppMessageType.recovercomment)) {
                RecoverCommentMessageBody body = (RecoverCommentMessageBody) dto.getMessageBodyObj();
                Game game = gameMap.get(body.getGameId());
                bodyText = messageSource.getMessage("wikiapp.messagetemplate." + dto.getMessageType().name(), new Object[]{body.getReason(), game.getName()}, Locale.CHINESE);
            }

            if (destUser != null) {
                dto.setDestUser(destUser);
            }

            dto.setBodyText(bodyText);
        });

        return dtoList;
    }


    /**
     * Get one appMessage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public AppUserMessage findOne(Long id) {
        log.debug("Request to get AppUserMessage : {}", id);
        AppUserMessage appUserMessage = redisAppUserMessageRepository.findOne(id);
        if (appUserMessage == null) {
            appUserMessage = appUserMessageRepository.findOneByIdAndValidStatus(id, ValidStatus.VALID);

            if (appUserMessage != null) {
                redisAppUserMessageRepository.save(appUserMessage);
            }
        }
        return appUserMessage;
    }

    @Override
    public boolean readAppMessage(Long uid, Long id) {
        log.debug("Request to read AppUserMessage : {}", id);
        AppUserMessage appUserMessage = findOne(id);
        if (appUserMessage.getUid() != uid) {
            return false;//todo
        }


        if (appUserMessage == null || appUserMessage.getReadStatus().equals(ValidStatus.VALID)) {
            return false;
        }

        appUserMessage.setReadStatus(ValidStatus.VALID);
        appUserMessageRepository.save(appUserMessage);
        redisAppUserMessageRepository.save(appUserMessage);
        redisAppUserMessageSumRepository.increaseMessageSum(appUserMessage.getAppkey(), appUserMessage.getUid(), -1);
        return true;
    }

    /**
     * Delete the  appMessage by id.
     *
     * @param id the id of the entity
     */
    @Override
    public boolean delete(Long id, Long uid) {
        log.debug("Request to delete AppUserMessage : {}", id);
        AppUserMessage appUserMessage = findOne(id);
        if (appUserMessage == null) {
            throw new BusinessException(getMessage("message.not.exists", null), "appkey");
        }

        if (appUserMessage.getValidStatus().equals(ValidStatus.UNVALID)) {
            throw new BusinessException(getMessage("message.has.delete", null), "appkey");
        }

        if (uid != appUserMessage.getUid()) {
            throw new BusinessException(getMessage("message.not.belong.to.user", null), "appkey");

        }


        appUserMessage.setValidStatus(ValidStatus.UNVALID);
        appUserMessageRepository.save(appUserMessage);

        redisAppUserMessageRepository.delete(id);
        redisAppUserMessageSetRepository.removeMessageId(appUserMessage.getAppkey(), appUserMessage.getUid(), appUserMessage.getId());
        return true;
    }

    @Override
    public AppUserMessageSum getAppMessageSum(String appkey, Long uid) {
        return redisAppUserMessageSumRepository.getMessageSum(appkey, uid);
    }

    private String getMessage(String key, Object[] param) {
        return messageSource.getMessage(key, param, Locale.CHINA);
    }
}
