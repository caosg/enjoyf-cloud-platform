package com.enjoyf.platform.userservice.service.mapper;

import com.enjoyf.platform.userservice.domain.UserAccount;
import com.enjoyf.platform.userservice.domain.UserLogin;
import com.enjoyf.platform.userservice.domain.UserProfile;
import com.enjoyf.platform.userservice.service.dto.RegisterReqDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 *
 * Created by shuguangcao on 2017/3/20.
 */
@Mapper
public interface RegisterMapper {
    RegisterMapper MAPPER = Mappers.getMapper(RegisterMapper.class);

    @Mappings({
        @Mapping(source = "nick", target = "loginName")
    })
    UserLogin toUserLogin(RegisterReqDTO registerReqDTO);

    UserAccount toUserAccount(RegisterReqDTO registerReqDTO);

    UserProfile toUserProfile(RegisterReqDTO registerReqDTO);
}
