package com.enjoyf.platform.userservice.config;

import com.enjoyf.platform.userservice.repository.redis.RedisAccountDTORepository;
import com.enjoyf.platform.userservice.repository.redis.RedisUserAccountRepository;
import com.enjoyf.platform.userservice.repository.redis.RedisUserProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.util.IdGenerator;
import org.springframework.util.SimpleIdGenerator;

/**
 * 用户服务配置类
 * Created by shuguangcao on 2017/3/20.
 */
@Configuration
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
public class UserServiceConfiguration  {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceConfiguration.class);

    @Bean
    public IdGenerator idGenerator(){
        return new SimpleIdGenerator();
    }

    @Bean
    public CommandLineRunner init(RedisAccountDTORepository redisAccountDTORepository, RedisUserAccountRepository redisUserAccountRepository,
                                  RedisUserProfileRepository redisUserProfileRepository){
        return (args)-> {
//            logger.info("****** clean up redis cache data ********");
//            todo 启动时候不用清楚缓存
//            redisUserAccountRepository.deleteAll();
//            redisUserProfileRepository.deleteAll();
//            redisAccountDTORepository.deleteAll();
        };
    }
}
