package com.enjoyf.platform.autoconfigure.context;

import com.enjoyf.platform.autoconfigure.web.CommonParams;

/**
 * Created by shuguangcao on 2017/6/29.
 */
public class CommonContextImpl implements CommonContext {

    private CommonParams commonParams;

    @Override
    public CommonParams getCommonParams() {
        return this.commonParams;
    }

    @Override
    public void setCommonParams(CommonParams commonParams) {
        this.commonParams = commonParams;
    }
}
