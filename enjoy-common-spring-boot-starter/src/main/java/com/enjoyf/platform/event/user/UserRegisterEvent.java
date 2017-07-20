package com.enjoyf.platform.event.user;

import com.enjoyf.platform.event.Event;

/**
 * Created by ericliu on 2017/5/10.
 */
@Deprecated
public class UserRegisterEvent extends Event {

    public UserRegisterEvent() {
        super(UserEventType.USER_REGISTER, UserEventConstants.BIND_KEY);
    }

    private String nick;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Override
    public String toString() {
        return "ContentInsertEvent{" +
                "nick='" + nick + '\'' +
                '}';
    }
}
