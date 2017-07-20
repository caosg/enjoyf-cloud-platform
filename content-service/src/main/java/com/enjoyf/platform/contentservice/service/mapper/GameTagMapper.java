package com.enjoyf.platform.contentservice.service.mapper;

import com.enjoyf.platform.contentservice.domain.GameTag;
import com.enjoyf.platform.contentservice.service.dto.GameTagDTO;
import com.enjoyf.platform.contentservice.web.rest.vm.GameDetailVM;
import com.enjoyf.platform.contentservice.web.rest.vm.GameTagVM;
import com.enjoyf.platform.contentservice.web.rest.vm.GameVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * Created by zhimingli on 2017/6/21.
 */
@Mapper
public interface GameTagMapper {

    GameTagMapper MAPPER = Mappers.getMapper(GameTagMapper.class);


    GameTagDTO toGameTagDTO(GameTag gameTag);


    GameTagVM toGameTagVM(GameTag gameTag);


    GameDetailVM toGameDetailVM(GameVM gameVM);
}
