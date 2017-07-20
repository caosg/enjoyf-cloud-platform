package com.enjoyf.platform.userservice.service.impl;

import com.enjoyf.platform.userservice.service.UserAccountService;
import com.enjoyf.platform.userservice.service.UserProfileService;
import com.enjoyf.platform.userservice.service.UserServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 用户中心服务门面类，复杂业务接口聚合
 * Created by shuguangcao on 2017/3/27.
 */
@Service
public class UserServiceFacadeImpl implements UserServiceFacade {

    private final static Logger log = LoggerFactory.getLogger(UserServiceFacadeImpl.class);

    private final UserAccountService userAccountService;

    private final UserProfileService userProfileService;

    public UserServiceFacadeImpl(UserAccountService userAccountService, UserProfileService userProfileService) {
        this.userAccountService = userAccountService;
        this.userProfileService = userProfileService;
    }


}
