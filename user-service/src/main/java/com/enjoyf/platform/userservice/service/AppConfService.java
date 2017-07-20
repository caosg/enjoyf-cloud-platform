package com.enjoyf.platform.userservice.service;

import com.enjoyf.platform.userservice.domain.AppConf;
import com.enjoyf.platform.userservice.domain.enumeration.ValidStatus;
import com.enjoyf.platform.userservice.repository.AppConfRepository;
import com.enjoyf.platform.userservice.repository.redis.RedisAppConfRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

/**
 * Created by shuguangcao on 2017/7/4.
 */
@Service
public class AppConfService {

    private final AppConfRepository appConfRepository;
    private final RedisAppConfRepository redisAppConfRepository;

    public AppConfService(AppConfRepository appConfRepository, RedisAppConfRepository redisAppConfRepository) {
        this.appConfRepository = appConfRepository;
        this.redisAppConfRepository = redisAppConfRepository;
    }

    @PostConstruct
    public void init(){
       redisAppConfRepository.save(appConfRepository.findAll()) ;
    }

    public AppConf getAppConfByAppKey(String appkey){
        AppConf appConf = null;
        if(StringUtils.isEmpty(appkey)) return appConf;
        appConf = redisAppConfRepository.findOneByAppKey(appkey);
        if (appConf==null)
            appConf = appConfRepository.findOneByAppKey(appkey);
        return appConf;
    }

    /**
     * 获取profileKey
     * @param appKey
     * @return
     */
    public String getProfileKeyByAppKey(String appKey){
        AppConf appConf = this.getAppConfByAppKey(appKey);
        if(appConf!=null&&appConf.getStatus()== ValidStatus.VALID)
            return appConf.getProfileKey().toString().toLowerCase();
        else
            return "";
    }
}
