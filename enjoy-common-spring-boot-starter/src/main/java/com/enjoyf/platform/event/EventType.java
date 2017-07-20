package com.enjoyf.platform.event;

/**
 * 事件类型抽象类
 */
public abstract class EventType {


    public EventType() {
    }

    protected EventType(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }

}
