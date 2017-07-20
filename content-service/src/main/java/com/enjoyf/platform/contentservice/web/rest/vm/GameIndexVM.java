package com.enjoyf.platform.contentservice.web.rest.vm;

/**
 * Created by zhimingli on 2017/6/27.
 */
public class GameIndexVM {
    private int type;  //1游戏 2标签 3推荐用户
    private Object data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public GameIndexVM(int type, Object data) {
        this.type = type;
        this.data = data;
    }

}
