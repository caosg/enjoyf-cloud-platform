package com.enjoyf.platform.profileservice.service.mapper;

import com.enjoyf.platform.profileservice.domain.VerifyProfile;
import com.enjoyf.platform.profileservice.domain.VertualProfile;
import com.enjoyf.platform.profileservice.service.userservice.dto.UserProfileDTO;
import com.enjoyf.platform.profileservice.web.rest.vm.VertualProfileVM;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity VerifyProfile and its DTO VerifyProfileDTO.
 */
@Mapper
public interface VertualProfileMapper {
    VertualProfileMapper MAPPER = Mappers.getMapper(VertualProfileMapper.class);

    UserProfileDTO vertualProfileVm2UserProfile(VertualProfileVM vertualProfileVM);

    VertualProfile userProfile2VertualProfile(UserProfileDTO userProfileDTO);

    default VerifyProfile fromId(Long id) {
        if (id == null) {
            return null;
        }
        VerifyProfile profile = new VerifyProfile();
        profile.setId(id);
        return profile;
    }
}
