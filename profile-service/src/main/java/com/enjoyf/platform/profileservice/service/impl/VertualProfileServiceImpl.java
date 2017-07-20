package com.enjoyf.platform.profileservice.service.impl;

import com.enjoyf.platform.profileservice.service.userservice.UserProfileFeignClient;
import com.enjoyf.platform.profileservice.service.userservice.dto.UserProfileDTO;
import com.enjoyf.platform.profileservice.service.VertualProfileService;
import com.enjoyf.platform.profileservice.domain.VertualProfile;
import com.enjoyf.platform.profileservice.repository.jpa.VertualProfileRepository;
import com.enjoyf.platform.profileservice.service.mapper.VertualProfileMapper;
import com.enjoyf.platform.profileservice.web.rest.vm.VertualProfileVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service Implementation for managing VertualProfile.
 */
@Service
public class VertualProfileServiceImpl implements VertualProfileService {

    private final Logger log = LoggerFactory.getLogger(VertualProfileServiceImpl.class);

    private final VertualProfileRepository vertualProfileRepository;
    private final UserProfileFeignClient userProfileFeignClient;

    public VertualProfileServiceImpl(VertualProfileRepository vertualProfileRepository, UserProfileFeignClient userProfileFeignClient) {
        this.vertualProfileRepository = vertualProfileRepository;
        this.userProfileFeignClient = userProfileFeignClient;
    }

    /**
     * Save a vertualProfile.
     *
     * @param vertualProfileVM the entity to createVertualProfile
     * @return the persisted entity
     */
    @Override
    public VertualProfile createVertualProfile(VertualProfileVM vertualProfileVM) {
        log.debug("Request to createVertualProfile VertualProfile : {}", vertualProfileVM);

        UserProfileDTO userProfileDTO = VertualProfileMapper.MAPPER.vertualProfileVm2UserProfile(vertualProfileVM);
        userProfileDTO.setAccountNo(UUID.randomUUID().toString());
        userProfileDTO = userProfileFeignClient.saveUserProfile(userProfileDTO);
        VertualProfile result = null;
        if (userProfileDTO != null) {
            VertualProfile vertualProfile = VertualProfileMapper.MAPPER.userProfile2VertualProfile(userProfileDTO);
            vertualProfile.setId(userProfileDTO.getId());
            result = vertualProfileRepository.save(vertualProfile);
        }

        return result;
    }

    @Override
    public VertualProfile updateVertualProfile(VertualProfile vertualProfile) {
        log.debug("Request to createVertualProfile vertualProfile : {}", vertualProfile);

        UserProfileDTO userProfileDTO = userProfileFeignClient.findOne(vertualProfile.getId());
        if (userProfileDTO == null) {
            log.error("update VertualProfile userProfileDTO is null: {}", vertualProfile);
            return null;
        }
        userProfileDTO.setIcon(vertualProfile.getIcon());
        userProfileDTO.setDiscription(vertualProfile.getDescription());
        userProfileDTO.setSex(vertualProfile.getSex());
        userProfileFeignClient.update(userProfileDTO);

        return vertualProfileRepository.save(vertualProfile);
    }

    /**
     * Get all the vertualProfiles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<VertualProfile> findAll(Pageable pageable) {
        log.debug("Request to get all VertualProfiles");
        Page<VertualProfile> result = vertualProfileRepository.findAll(pageable);
        return result;
    }

    /**
     * Get one vertualProfile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public VertualProfile findOne(Long id) {
        log.debug("Request to get VertualProfile : {}", id);
        VertualProfile vertualProfile = vertualProfileRepository.findOne(id);
        return vertualProfile;
    }

    @Override
    public List<VertualProfile> findAllByNickLike(String nick) {
        log.debug("Request to get VertualProfile : {}", nick);
        return vertualProfileRepository.findByNickContaining(nick);
    }

    @Override
    public Map<Long, VertualProfile> findByIds(Long[] ids) {
        log.debug("Request to get VertualProfile : {}", ids);

        Map<Long, VertualProfile> result = new HashMap<>();

        vertualProfileRepository.findByIdIn(ids).stream().forEach(vertualProfile -> {
            result.put(vertualProfile.getId(), vertualProfile);
        });

        return result;
    }

    /**
     * Delete the  vertualProfile by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VertualProfile : {}", id);
        vertualProfileRepository.delete(id);
    }
}
