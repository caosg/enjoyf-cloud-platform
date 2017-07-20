package com.enjoyf.platform.messageservice.event;

import com.enjoyf.platform.common.util.StringUtil;
import com.enjoyf.platform.event.Event;
import com.enjoyf.platform.event.EventConstants;
import com.enjoyf.platform.event.EventReceiver;
import com.enjoyf.platform.event.EventSender;
import com.enjoyf.platform.event.message.MessageEventConstants;
import com.enjoyf.platform.event.message.MessageAppPushEvent;
import com.enjoyf.platform.event.message.wikiapp.*;
import com.enjoyf.platform.messageservice.domain.AppUserMessage;
import com.enjoyf.platform.messageservice.domain.PushApp;
import com.enjoyf.platform.messageservice.domain.enumration.AppMessageType;
import com.enjoyf.platform.messageservice.service.*;
import com.enjoyf.platform.messageservice.service.dto.PushMessageDTO;
import com.enjoyf.platform.messageservice.service.dto.MessageBody;
import com.enjoyf.platform.messageservice.service.mapper.PushMessageMapper;
import com.enjoyf.platform.messageservice.service.mapper.WikiAppUserMessageMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by ericliu on 2017/5/9.
 */
@Component
public class MessageEventProcess implements EventSender, EventReceiver {
    private static Logger log = LoggerFactory.getLogger(MessageEventProcess.class);

    private final AmqpAdmin amqpAdmin;
    private final RabbitTemplate amqpTemplate;

    private final PushProfileDeviceService pushProfileDeviceService;
    private final PushAppService pushAppService;
    private final PushMessageService pushMessageService;
    private final AppUserMessageService appUserMessageService;
    private final AppPushMessageService appPushMessageService;

    private final ObjectMapper objectMapper;

    public MessageEventProcess(AmqpAdmin amqpAdmin, RabbitTemplate amqpTemplate,
                               PushProfileDeviceService pushProfileDeviceService,
                               PushAppService pushAppService,
                               PushMessageService pushMessageService,
                               AppUserMessageService appUserMessageService,
                               AppPushMessageService appPushMessageService,
                               ObjectMapper objectMapper
    ) {
        this.amqpAdmin = amqpAdmin;
        this.amqpTemplate = amqpTemplate;
        this.pushProfileDeviceService = pushProfileDeviceService;
        this.pushMessageService = pushMessageService;
        this.pushAppService = pushAppService;
        this.objectMapper = objectMapper;
        this.appUserMessageService = appUserMessageService;
        this.appPushMessageService = appPushMessageService;
        declareQueue();

        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        this.amqpTemplate.setMessageConverter(converter);
    }


    @Override
    public void send(Event event) {
        amqpTemplate.setExchange(EventConstants.EVENT_EXCHANGE);
        amqpTemplate.setRoutingKey(event.getBindKey());
        amqpTemplate.convertAndSend(event);
    }

    @Override
    public void receiveEvent(Event event) {
        if (event instanceof MessageAppPushEvent) {
            log.info("========MessageAppPushEvent : {} ", event);
            MessageAppPushEvent messageAppPushEvent = (MessageAppPushEvent) event;
            pushMessage(messageAppPushEvent);

            appPushMessageService.modifySendSatus(messageAppPushEvent.getPushMessageId());

        } else if (event instanceof WikiAppMessageEvent) {

            AppUserMessage appUserMessage = toWikiAppUserMessage((WikiAppMessageEvent) event);

            appUserMessage = appUserMessageService.save(appUserMessage);
            log.info("save appUserMessage:", appUserMessage);
            //build dto to push
//            Set<AppUserMessage> messageSet = new HashSet<>();
//            messageSet.add(appUserMessage);
//            List<AppUserMessageDTO> dtoList = appUserMessageService.buildWikiAppUserMessageDTO(messageSet);
//            if (CollectionUtils.isEmpty(dtoList)) {
//                return;
//            }

//            AppUserMessageDTO dto = dtoList.get(0);
//            log.info("after save dto:{}", dto);
            //pushmessage 暫時先不需要
//            MessageAppPushEvent messageSendEvent = new MessageAppPushEvent();
//            messageSendEvent.setAppkey(dto.getAppkey());
//            messageSendEvent.setUid(dto.getUid());
//            messageSendEvent.setTitle(dto.getBodyText());//todo
//            messageSendEvent.setBody(dto.getBodyText());
//            Map<String, String> map = new HashMap<>();
//            map.put("jt", String.valueOf(dto.getJt()));
//            map.put("ji", dto.getJi());
//            messageSendEvent.setExtras(map);
//
//            pushMessage(messageSendEvent);
        } else {
            //
            log.info("========receiveEvent other: {} ", event);
        }
    }

    private AppUserMessage toWikiAppUserMessage(WikiAppMessageEvent event) {
        AppUserMessage appUserMessage = new AppUserMessage();

        AppMessageType messageType = null;
        MessageBody messageBody = null;
        String appkey = "";
        long uid = 0l;
        int jt = 0;
        String ji = "";//todo
        if (event instanceof WikiAppMessageReplyNewsEvent) {
            messageType = AppMessageType.replynews;
            WikiAppMessageReplyNewsEvent replyNewsEvent = (WikiAppMessageReplyNewsEvent) event;
            messageBody = WikiAppUserMessageMapper.MAPPER.event2ReplyNewsBody(replyNewsEvent);
            appkey = replyNewsEvent.getAppkey();
            uid = replyNewsEvent.getUid();
            jt = -2;
            ji = "";//url
        } else if (event instanceof WikiAppMessageReplyCommentEvent) {
            messageType = AppMessageType.replycomment;
            WikiAppMessageReplyCommentEvent replyCommentEvent = (WikiAppMessageReplyCommentEvent) event;
            messageBody = WikiAppUserMessageMapper.MAPPER.event2ReplyCommentBody(replyCommentEvent);
            appkey = replyCommentEvent.getAppkey();
            uid = replyCommentEvent.getUid();
            jt = 200;
            ji = String.valueOf(replyCommentEvent.getCommentId());
        } else if (event instanceof WikiAppMessageCommentUsefulEvent) {
            messageType = AppMessageType.commentuseful;
            WikiAppMessageCommentUsefulEvent commentUsefulEvent = (WikiAppMessageCommentUsefulEvent) event;
            appkey = commentUsefulEvent.getAppkey();
            uid = commentUsefulEvent.getUid();
            messageBody = WikiAppUserMessageMapper.MAPPER.event2CommentUserfulBody(commentUsefulEvent);
            jt = 200;
            ji = String.valueOf(commentUsefulEvent.getCommentId());
        } else if (event instanceof WikiAppMessageFeedbackCommentEvent) {
            messageType = AppMessageType.feedbackcomment;
            WikiAppMessageFeedbackCommentEvent feedbackCommentEvent = (WikiAppMessageFeedbackCommentEvent) event;
            appkey = feedbackCommentEvent.getAppkey();
            uid = feedbackCommentEvent.getUid();
            messageBody = WikiAppUserMessageMapper.MAPPER.event2FeedbackCommentBody(feedbackCommentEvent);
        } else if (event instanceof WikiAppMessageFeedbackGameEvent) {
            messageType = AppMessageType.feedbackgame;
            WikiAppMessageFeedbackGameEvent feedbackGameEvent = (WikiAppMessageFeedbackGameEvent) event;
            appkey = feedbackGameEvent.getAppkey();
            uid = feedbackGameEvent.getUid();
            jt = 0;
            messageBody = WikiAppUserMessageMapper.MAPPER.event2FeedbackGameBody(feedbackGameEvent);
        } else if (event instanceof WikiAppMessageFeedbackUserEvent) {
            messageType = AppMessageType.feedbackuser;
            WikiAppMessageFeedbackUserEvent feedbackUserEvent = (WikiAppMessageFeedbackUserEvent) event;
            appkey = feedbackUserEvent.getAppkey();
            uid = feedbackUserEvent.getUid();
            jt = 0;
            messageBody = WikiAppUserMessageMapper.MAPPER.event2FeedbackUserBody(feedbackUserEvent);
        } else if (event instanceof WikiAppMessageDeleteCommentEvent) {
            messageType = AppMessageType.deletecomment;
            WikiAppMessageDeleteCommentEvent deleteCommentEvent = (WikiAppMessageDeleteCommentEvent) event;
            appkey = deleteCommentEvent.getAppkey();
            uid = deleteCommentEvent.getUid();
            messageBody = WikiAppUserMessageMapper.MAPPER.event2DeleteCommentBody(deleteCommentEvent);
            jt = 0;
        } else if (event instanceof WikiAppMessageRecoverCommentEvent) {
            messageType = AppMessageType.recovercomment;
            WikiAppMessageRecoverCommentEvent recoverCommentEvent = (WikiAppMessageRecoverCommentEvent) event;
            appkey = recoverCommentEvent.getAppkey();
            uid = recoverCommentEvent.getUid();
            messageBody = WikiAppUserMessageMapper.MAPPER.event2RecoverCommentBody(recoverCommentEvent);
            jt = 200;
            ji = String.valueOf(recoverCommentEvent.getCommentId());
        } else {
            log.error("not support this WikiAppMessageEvent.event:{}", event);
            return null;
        }

        if (!messageBody.checkBody() || uid <= 0l || StringUtil.isEmpty(appkey)) {
            log.error("app usermessage illegal.messageBody:{},uid:{}.appkey：{}", messageBody, uid, appkey);
            return null;
        }

        try {
            appUserMessage.setMessageBody(objectMapper.writeValueAsString(messageBody));
        } catch (JsonProcessingException e) {
            log.error("message body prase occured json error. event: " + event, e);
            return null;
        }
        appUserMessage.setAppkey(appkey);
        appUserMessage.setUid(uid);
        appUserMessage.setMessageType(messageType);
        appUserMessage.setJt(jt);
        appUserMessage.setJi(ji);

        return appUserMessage;
    }


    //发送系统消息
    private void pushMessage(MessageAppPushEvent sendEvent) {
        log.info("========MessageAppPushEvent : {} ", sendEvent);

        PushApp pushApp = pushAppService.findOneByAppKey(sendEvent.getAppkey());
        if (pushApp == null) {
            return;
        }

        //if not sendAlltodo
        PushMessageDTO pushMessageDTO = PushMessageMapper.MAPPER.toPushMessageDTO(sendEvent);
        Map<String, String> extras = new HashMap<>();
        extras.put("jt", String.valueOf(sendEvent.getJt()));
        extras.put("ji", sendEvent.getJi());
        pushMessageDTO.setExtras(extras);
        System.out.println(pushMessageDTO);
        if (pushMessageDTO.getPushType().isDevice()) {
            String[] device = pushProfileDeviceService.getPushProfileDevice(sendEvent.getAppkey(), sendEvent.getPushUid());
            if (device == null) {
                return;
            }
            if (device.length != 2) {
                log.error("device key error.sendEvent:{}", sendEvent);
                return;
            }
            pushMessageDTO.setDevice(device[0]);
            pushMessageDTO.setPlatform(Integer.parseInt(device[1]));
            pushMessageService.pushMessage(pushApp, pushMessageDTO);
        } else {
            pushMessageService.pushMessage(pushApp, pushMessageDTO);
        }

    }

    private void declareQueue() {
        Exchange e = ExchangeBuilder.topicExchange(EventConstants.EVENT_EXCHANGE).build();
        Queue q = new Queue(MessageEventConstants.QUEUE_NAME);
        Binding bind = BindingBuilder.bind(q).to(e).with(MessageEventConstants.BIND_KEY).noargs();

        amqpAdmin.declareQueue(q);
        amqpAdmin.declareExchange(e);
        amqpAdmin.declareBinding(bind);
    }


    @RabbitListener(queues = MessageEventConstants.QUEUE_NAME + "")
    public void receiveUserMessage(Message message) {
        Event event = (Event) amqpTemplate.getMessageConverter().fromMessage(message);
        log.info("========receiveEvent: {} , message routing keys: {}", message, message.getMessageProperties().getReceivedRoutingKey());
        try {
            receiveEvent(event);
        } catch (Exception e) {
            log.error("receiveEvent occured error.e:", e);
        }
    }
}
