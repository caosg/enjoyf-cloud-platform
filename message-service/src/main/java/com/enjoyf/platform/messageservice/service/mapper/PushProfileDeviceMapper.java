package com.enjoyf.platform.messageservice.service.mapper;

import com.enjoyf.platform.messageservice.domain.PushProfileDevice;
import com.enjoyf.platform.messageservice.service.dto.PushProfileDeviceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity PushApp and its DTO PushAppDTO.
 */
@Mapper
public interface PushProfileDeviceMapper {
    PushProfileDeviceMapper MAPPER = Mappers.getMapper(PushProfileDeviceMapper.class);

    PushProfileDevice toEntity(PushProfileDeviceDTO dto);

    PushProfileDeviceDTO toDTO(PushProfileDevice dto);
}
