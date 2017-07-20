package com.enjoyf.platform.messageservice.service.dto;

import com.enjoyf.platform.messageservice.domain.enumration.AppMessageType;

/**
 * Created by ericliu on 2017/6/19.
 */
public interface MessageBody {

    /**
     * 检查MessageBody里的参数和格式是否合法。
     * @return true合法 false不合法
     */
    boolean checkBody();
}
