package com.enjoyf.platform.messageservice;

/**
 * Created by ericliu on 2017/5/27.
 */
public class PushConstants {
    public final static int PUSH_BY_DEVICE = 0;
    public final static int PUSH_BY_TAG = 1;

    public static boolean isPushByDevice(int type) {
        return type == PUSH_BY_DEVICE;
    }

    public static boolean isPushByTag(int type) {
        return type == PUSH_BY_TAG;
    }
}
