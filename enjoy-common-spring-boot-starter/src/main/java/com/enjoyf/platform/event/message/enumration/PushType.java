package com.enjoyf.platform.event.message.enumration;

/**
 * Created by ericliu on 2017/7/6.
 */
public enum PushType {
    DEVICE, TAG;


    public boolean isDevice() {
        return this.equals(DEVICE);
    }

    public boolean isTag() {
        return this.equals(TAG);
    }
}
