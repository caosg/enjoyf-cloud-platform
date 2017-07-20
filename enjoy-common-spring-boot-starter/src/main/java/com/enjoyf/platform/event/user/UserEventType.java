package com.enjoyf.platform.event.user;

import com.enjoyf.platform.event.EventType;

/**
 * Created by ericliu on 2017/5/10.
 */
public class UserEventType extends EventType {

    public static final UserEventType USER_REGISTER = new UserEventType("user.register");


    public UserEventType(String type) {
        super(type);
    }

    public UserEventType() {
        super();
    }
}
