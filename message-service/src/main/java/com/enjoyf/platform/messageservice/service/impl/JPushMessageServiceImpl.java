package com.enjoyf.platform.messageservice.service.impl;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.enjoyf.platform.messageservice.PushConstants;
import com.enjoyf.platform.messageservice.domain.Platform;
import com.enjoyf.platform.messageservice.domain.PushApp;
import com.enjoyf.platform.messageservice.service.PushMessageService;
import com.enjoyf.platform.messageservice.service.dto.PushMessageDTO;
import com.enjoyf.platform.messageservice.service.exception.MessageException;
import com.google.common.base.Strings;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by ericliu on 2017/5/26.
 */
@Component
public class JPushMessageServiceImpl implements PushMessageService {

    private Logger logger = LoggerFactory.getLogger(JPushMessageServiceImpl.class);

    @Override
    public void pushMessage(PushApp pushApp, PushMessageDTO pushMessageDTO) throws MessageException {

        JPushClient jPushClient = new JPushClient(pushApp.getThirdSecrkey(), pushApp.getThirdAppkey());
        PushPayload payload = buildPushPayLoad(pushMessageDTO);
        try {
            PushResult result = jPushClient.sendPush(payload);

            logger.info("jiguang push result:{} ", result);
        } catch (APIConnectionException e) {
            logger.error("jiguang push error:{} ", e);
        } catch (APIRequestException e) {
            logger.error("jiguang push error:{} ", e);
        }
    }


    public static PushPayload buildPushPayLoad(PushMessageDTO pushMessageDTO) {
        PushPayload.Builder builder = PushPayload.newBuilder();

        if (Platform.isAndroid(pushMessageDTO.getPlatform())) {

            builder = builder
                .setPlatform(cn.jpush.api.push.model.Platform.android())
                .setNotification(Notification.newBuilder()
                    .addPlatformNotification(AndroidNotification.newBuilder()
                        .setAlert(pushMessageDTO.getTitle())
                        .addExtras(pushMessageDTO.getExtras())
                        .build()).build());

        } else if (Platform.isIOS(pushMessageDTO.getPlatform())) {
            builder = builder
                .setPlatform(cn.jpush.api.push.model.Platform.ios())
                .setNotification(Notification.newBuilder()
                    .addPlatformNotification(IosNotification.newBuilder()
                        .setAlert(pushMessageDTO.getTitle())
                        .setSound("default")
                        .setBadge(1)
                        .addExtras(pushMessageDTO.getExtras())
                        .build()).build());
        }

        Audience audience = null;
        if (pushMessageDTO.getPushType().isDevice()) {
            audience = Audience.registrationId(pushMessageDTO.getDevice());
        } else if (pushMessageDTO.getPushType().isTag()) {
            String[] tags = pushMessageDTO.getTags().split(",");
            if (!ArrayUtils.isEmpty(tags)) {
                audience = Audience.tag_and(tags);
            }else{
                audience = Audience.all();
            }
        }

        builder = builder.setAudience(audience);

        Message.Builder messageBuilder = Message.newBuilder().setTitle(pushMessageDTO.getTitle());
        if (!Strings.isNullOrEmpty(pushMessageDTO.getBody())) {
            messageBuilder.setMsgContent(pushMessageDTO.getBody());
        } else {
            messageBuilder.setMsgContent(pushMessageDTO.getTitle());
        }

        return builder.setMessage(messageBuilder.build()).build();
    }


}
