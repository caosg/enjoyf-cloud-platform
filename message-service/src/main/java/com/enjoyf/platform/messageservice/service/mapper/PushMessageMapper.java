package com.enjoyf.platform.messageservice.service.mapper;

import com.enjoyf.platform.event.message.MessageAppPushEvent;
import com.enjoyf.platform.messageservice.domain.AppPushMessage;
import com.enjoyf.platform.messageservice.domain.AppUserMessage;
import com.enjoyf.platform.messageservice.service.dto.PushMessageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * vm-- envent
 * event-- dto
 * Created by ericliu on 2017/5/27.
 */
@Mapper
public interface PushMessageMapper {
    PushMessageMapper MAPPER = Mappers.getMapper(PushMessageMapper.class);

    @Mappings({
        @Mapping(source = "id", target = "pushMessageId")
    })
    MessageAppPushEvent toMessageAppPushEvent(AppPushMessage appPushMessage);

    /**
     * event to pushmessagedto
     *
     * @param messageAppPushEvent
     * @return
     */
    PushMessageDTO toPushMessageDTO(MessageAppPushEvent messageAppPushEvent);
}
