package com.enjoyf.platform.userservice.service.mapper;

import com.enjoyf.platform.userservice.domain.UserAccount;
import com.enjoyf.platform.userservice.domain.UserLogin;
import com.enjoyf.platform.userservice.domain.UserProfile;
import com.enjoyf.platform.userservice.service.dto.SocialAuthDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * Created by shuguangcao on 2017/5/8.
 */
@Mapper
public interface SocialMapper {

    SocialMapper MAPPER = Mappers.getMapper(SocialMapper.class);

    @Mappings({
        @Mapping(source = "nick", target = "loginName")
    })
    UserLogin toUserLogin(SocialAuthDTO socialAuthDTO);

    UserAccount toUserAccount(SocialAuthDTO socialAuthDTO);

    UserProfile toUserProfile(SocialAuthDTO socialAuthDTO);
}
