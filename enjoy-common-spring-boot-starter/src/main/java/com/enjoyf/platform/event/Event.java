package com.enjoyf.platform.event;

/**
 * 事件抽象类
 */
public abstract class Event {

    private String eventType;//事件类型

    private String bindKey;//确定往哪个服务发送事件

    public Event(EventType eventType, String bindKey) {
        this.eventType = eventType.getType();
        this.bindKey = bindKey;
    }

    public String getBindKey() {
        return bindKey;
    }

    public String getEventType() {
        return eventType;
    }

}
