package com.enjoyf.platform.messageservice.service;

import com.enjoyf.platform.common.domain.enumeration.ValidStatus;
import com.enjoyf.platform.event.message.MessageAppPushEvent;
import com.enjoyf.platform.messageservice.domain.AppPushMessage;
import com.enjoyf.platform.messageservice.domain.enumration.SendType;
import com.enjoyf.platform.messageservice.event.MessageEventProcess;
import com.enjoyf.platform.messageservice.service.mapper.PushMessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by ericliu on 2017/7/10.
 */
@Component
public class SechduleSubscribeMessage {

    private final Logger log = LoggerFactory.getLogger(SechduleSubscribeMessage.class);


    private final AppPushMessageService appPushMessageService;
    private final MessageEventProcess messageEventProcess;

    public SechduleSubscribeMessage(AppPushMessageService appPushMessageService, MessageEventProcess messageEventProcess) {
        this.appPushMessageService = appPushMessageService;
        this.messageEventProcess = messageEventProcess;
    }


    @Scheduled(cron = "0/30 * * * * *")
    @Transactional
    public void sendSubscribeMessage() {
        log.info("sendSubscribeMessage");

        List<AppPushMessage> list = appPushMessageService.findAllSubscribeMessageList(
            ZonedDateTime.now(),
            SendType.delayed,
            ValidStatus.INIT,
            ValidStatus.UNVALID
        );

        list.stream().forEach(appPushMessage -> {
            MessageAppPushEvent appPushEvent = PushMessageMapper.MAPPER.toMessageAppPushEvent(appPushMessage);
            messageEventProcess.send(appPushEvent);
        });

    }
}
