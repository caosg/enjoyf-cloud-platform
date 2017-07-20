package com.enjoyf.platform.messageservice.service.mapper;

import com.enjoyf.platform.event.message.wikiapp.*;
import com.enjoyf.platform.messageservice.service.dto.wikiapp.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by ericliu on 2017/6/19.
 */
@Mapper
public interface WikiAppUserMessageMapper {
    WikiAppUserMessageMapper MAPPER = Mappers.getMapper(WikiAppUserMessageMapper.class);

    CommentUsefulMessageBody event2CommentUserfulBody(WikiAppMessageCommentUsefulEvent event);

    ReplyCommentMessageBody event2ReplyCommentBody(WikiAppMessageReplyCommentEvent event);

    ReplyNewsMessageBody event2ReplyNewsBody(WikiAppMessageReplyNewsEvent event);

    FeedbackCommentMessageBody event2FeedbackCommentBody(WikiAppMessageFeedbackCommentEvent event);

    FeedbackGameMessageBody event2FeedbackGameBody(WikiAppMessageFeedbackGameEvent event);

    FeedbackUserMessageBody event2FeedbackUserBody(WikiAppMessageFeedbackUserEvent event);

    DeleteCommentMessageBody event2DeleteCommentBody(WikiAppMessageDeleteCommentEvent event);

    RecoverCommentMessageBody event2RecoverCommentBody(WikiAppMessageRecoverCommentEvent event);
}
