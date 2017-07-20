package com.enjoyf.platform.profileservice.service.mapper;

import com.enjoyf.platform.profileservice.domain.*;
import com.enjoyf.platform.profileservice.service.dto.VerifyProfileDTO;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity VerifyProfile and its DTO VerifyProfileDTO.
 */
@Mapper
public interface VerifyProfileMapper {
    VerifyProfileMapper MAPPER = Mappers.getMapper(VerifyProfileMapper.class);

    VerifyProfileDTO toVerifyProfileDTO(VerifyProfile verifyProfile);

    default VerifyProfile fromId(Long id) {
        if (id == null) {
            return null;
        }
        VerifyProfile profile = new VerifyProfile();
        profile.setId(id);
        return profile;
    }
}
