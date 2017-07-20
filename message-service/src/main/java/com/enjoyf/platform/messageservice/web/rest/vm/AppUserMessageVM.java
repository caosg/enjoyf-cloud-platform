package com.enjoyf.platform.messageservice.web.rest.vm;

/**
 * Created by ericliu on 2017/6/19.
 */
public class AppUserMessageVM {

    private long jt;
    private long ji;
    private int readStatus;
    private String body;

    public long getJt() {
        return jt;
    }

    public void setJt(long jt) {
        this.jt = jt;
    }

    public long getJi() {
        return ji;
    }

    public void setJi(long ji) {
        this.ji = ji;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
