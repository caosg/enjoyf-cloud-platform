package com.enjoyf.platform.event;

/**
 * Created by ericliu on 2017/5/10.
 */
public interface EventSender {

    /**
     * 发送事件的方法
     * @param event
     */
    void send(Event event);
}
