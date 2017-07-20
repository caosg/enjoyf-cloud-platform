package com.enjoyf.platform.autoconfigure.storage;

import com.enjoyf.platform.autoconfigure.storage.local.LocalStorageService;
import com.enjoyf.platform.autoconfigure.storage.qiniu.QiNiuStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shuguangcao on 2017/6/30.
 */
@Configuration
@ConditionalOnClass({StorageService.class})
@EnableConfigurationProperties(StorageProperties.class)
public class StorageAutoConfiguration {
    @Autowired
    private StorageProperties storageProperties;

    @Bean
    @ConditionalOnMissingBean(StorageService.class)
    public StorageService storageService(){
        StorageService storageService = null;
        StorageProvider storageProvider = storageProperties.getProvider();
        if(storageProvider == null){
            storageProvider = StorageProvider.LOCAL;
        }
        switch (storageProvider) {
            case LOCAL:
                storageService=new LocalStorageService(storageProperties);
                break;
            case FASTDFS:
                break;
            case QINIU:
                storageService = new QiNiuStorageService(storageProperties);
                break;
            case ALIOSS:
                break;

        }
        return storageService;

    }
}
