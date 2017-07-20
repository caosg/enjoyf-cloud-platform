package com.enjoyf.platform.event.message;

import com.enjoyf.platform.event.EventType;

/**
 * Created by ericliu on 2017/5/10.
 */
public class MessageEventType extends EventType {

    public static final MessageEventType CREATE_DEVICERELATION = new MessageEventType("message.create.devicerelation");

    public static final MessageEventType BREAK_DEVICERELATION = new MessageEventType("message.break.devicerelation");

    public static final MessageEventType APP_PUSH_MESSAGE = new MessageEventType("message.app.pushmessage");

    public MessageEventType(String type) {
        super(type);
    }

    public MessageEventType() {
        super();
    }
}
