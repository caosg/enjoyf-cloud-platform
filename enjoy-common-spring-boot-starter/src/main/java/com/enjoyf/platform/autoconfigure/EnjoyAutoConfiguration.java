package com.enjoyf.platform.autoconfigure;

import com.enjoyf.platform.autoconfigure.security.jwt.EnjoyTokenProvider;
import com.enjoyf.platform.autoconfigure.security.jwt.JWTConfigurer;
import com.enjoyf.platform.autoconfigure.security.jwt.TokenProvider;
import com.enjoyf.platform.autoconfigure.storage.StorageAutoConfiguration;
import com.enjoyf.platform.autoconfigure.web.EnjoyWebMvcConfigurer;
import io.github.jhipster.config.JHipsterProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * spring boot auto configuration
 * Created by shuguangcao on 2017/6/27.
 */
@Configuration
@ComponentScan("com.enjoyf.platform.autoconfigure")
@AutoConfigureBefore(WebSecurityConfigurerAdapter.class)
@ImportAutoConfiguration(value = {EnjoyWebMvcConfigurer.class, StorageAutoConfiguration.class})
public class EnjoyAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TokenProvider tokenProvider(JHipsterProperties jHipsterProperties) {
        return new EnjoyTokenProvider(jHipsterProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public JWTConfigurer jwtConfigurer(TokenProvider tokenProvider) {
        return new JWTConfigurer(tokenProvider);
    }


}
