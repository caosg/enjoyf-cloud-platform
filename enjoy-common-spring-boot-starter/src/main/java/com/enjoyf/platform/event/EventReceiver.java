
package com.enjoyf.platform.event;

/**
 * 事件接收类
 */
public interface EventReceiver {

    /**
     * 事件接收后处理逻辑
     * @param event
     */
    void receiveEvent(Event event);
}
