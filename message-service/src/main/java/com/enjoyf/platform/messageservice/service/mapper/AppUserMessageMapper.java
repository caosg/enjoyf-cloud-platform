package com.enjoyf.platform.messageservice.service.mapper;

import com.enjoyf.platform.event.message.wikiapp.WikiAppMessageCommentUsefulEvent;
import com.enjoyf.platform.event.message.wikiapp.WikiAppMessageReplyCommentEvent;
import com.enjoyf.platform.event.message.wikiapp.WikiAppMessageReplyNewsEvent;
import com.enjoyf.platform.messageservice.domain.AppUserMessage;
import com.enjoyf.platform.messageservice.service.dto.AppUserMessageDTO;
import com.enjoyf.platform.messageservice.service.dto.wikiapp.CommentUsefulMessageBody;
import com.enjoyf.platform.messageservice.service.dto.wikiapp.ReplyCommentMessageBody;
import com.enjoyf.platform.messageservice.service.dto.wikiapp.ReplyNewsMessageBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by ericliu on 2017/6/19.
 */
@Mapper
public interface AppUserMessageMapper {
    AppUserMessageMapper MAPPER = Mappers.getMapper(AppUserMessageMapper.class);

    AppUserMessageDTO toDTO(AppUserMessage message);

}
