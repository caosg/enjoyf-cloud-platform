package com.enjoyf.platform.profileservice.service.impl;

import com.enjoyf.platform.common.util.CollectionUtil;
import com.enjoyf.platform.page.ScoreRange;
import com.enjoyf.platform.page.ScoreRangeRows;
import com.enjoyf.platform.profileservice.domain.VerifyProfile;
import com.enjoyf.platform.profileservice.domain.enumeration.VerifyProfileType;
import com.enjoyf.platform.profileservice.service.userservice.UserProfileFeignClient;
import com.enjoyf.platform.profileservice.service.userservice.dto.UserProfileDTO;
import com.enjoyf.platform.profileservice.repository.jpa.VerifyProfileRepository;
import com.enjoyf.platform.profileservice.repository.redis.RedisVerifyProfileRepository;
import com.enjoyf.platform.profileservice.repository.redis.RedisVerifyProfileSetRepository;
import com.enjoyf.platform.profileservice.service.VerifyProfileService;
import com.enjoyf.platform.profileservice.service.dto.VerifyProfileDTO;
import com.enjoyf.platform.profileservice.service.mapper.VerifyProfileMapper;
import com.sun.mail.imap.protocol.UIDSet;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service Implementation for managing VerifyProfile.
 */
@Service
public class VerifyProfileServiceImpl implements VerifyProfileService {

    private final Logger log = LoggerFactory.getLogger(VerifyProfileServiceImpl.class);

    private final VerifyProfileRepository verifyProfileRepository;

    private final UserProfileFeignClient userProfileFeignClient;

    private final RedisVerifyProfileRepository redisVerifyProfileRepository;

    private final RedisVerifyProfileSetRepository redisVerifyProfileSetRepository;


    public VerifyProfileServiceImpl(VerifyProfileRepository verifyProfileRepository,
                                    UserProfileFeignClient userProfileFeignClient,
                                    RedisVerifyProfileRepository redisVerifyProfileRepository,
                                    RedisVerifyProfileSetRepository redisVerifyProfileSetRepository) {
        this.verifyProfileRepository = verifyProfileRepository;
        this.userProfileFeignClient = userProfileFeignClient;
        this.redisVerifyProfileRepository = redisVerifyProfileRepository;
        this.redisVerifyProfileSetRepository = redisVerifyProfileSetRepository;
    }

    /**
     * Save a talent.
     *
     * @param verifyProfile the entity to createVertualProfile
     * @return the persisted entity
     */
    @Override
    public VerifyProfile save(VerifyProfile verifyProfile) {
        log.debug("Request to createVertualProfile VerifyProfile : {}", verifyProfile);

        verifyProfile = verifyProfileRepository.save(verifyProfile);

        redisVerifyProfileRepository.save(verifyProfile);
        redisVerifyProfileSetRepository.addVerifyProfile(verifyProfile);

        return verifyProfile;
    }


    @Override
    public Page<VerifyProfileDTO> findByVerifyType(VerifyProfileType verifyProfileType, Pageable pageable) {
        log.debug("Request to createVertualProfile findByVerifyType : {}, pageable : {}", verifyProfileType, pageable);

        Page<VerifyProfile> verifyProfiles = verifyProfileRepository.findAllByVerifyType(verifyProfileType, pageable);
        return pageEntitytopageDTO(verifyProfiles);
    }

    @Override
    public Page<VerifyProfileDTO> findAll(Pageable pageable) {

        Page<VerifyProfile> verifyProfiles = verifyProfileRepository.findAll(pageable);
        return pageEntitytopageDTO(verifyProfiles);
    }

    private Page<VerifyProfileDTO> pageEntitytopageDTO(Page<VerifyProfile> verifyProfiles) {
        Long[] ids = new Long[]{};
        ids = verifyProfiles.map(VerifyProfile::getId)
            .getContent().toArray(ids);

        if (ArrayUtils.isEmpty(ids)) {
            return null;
        }

        //build userprofile by feign
        List<UserProfileDTO> userProfileDTOS = userProfileFeignClient.findUserProfilesByUids(ids);
        Map<Long, UserProfileDTO> map = new HashMap<>();
        for (UserProfileDTO userProfileDTO : userProfileDTOS) {
            map.put(userProfileDTO.getId(), userProfileDTO);
        }

        return verifyProfiles.map(verifyProfile -> {
            UserProfileDTO userProfileDTO = map.get(verifyProfile.getId());
            if (userProfileDTO != null) {
                return entityToVerifyProfileDTO(verifyProfile, userProfileDTO);
            }
            return null;
        });
    }

    @Override
    public ScoreRangeRows<VerifyProfileDTO> findByVerifyType(VerifyProfileType verifyProfileType, ScoreRange scoreRange) {
        ScoreRangeRows<VerifyProfileDTO> result = new ScoreRangeRows<>();
        Set<Long> ids = redisVerifyProfileSetRepository.findAllByVerifyType(verifyProfileType, scoreRange);
        result.setRange(scoreRange);

        if (CollectionUtil.isEmpty(ids)) {
            return result;
        }

        Map<Long, VerifyProfile> verifyProfileMap = new HashMap<>();
        for (Long id : ids) {
            VerifyProfile verifyProfile = getVerifyProfile(id);
            if (verifyProfile != null) {
                verifyProfileMap.put(id, verifyProfile);
            }
        }
        if (CollectionUtil.isEmpty(verifyProfileMap)) {
            return result;
        }

        Long[] findIds = new Long[]{};
        findIds = verifyProfileMap.keySet().toArray(findIds);
        List<UserProfileDTO> userProfileDTOS = userProfileFeignClient.findUserProfilesByUids(findIds);
        Map<Long, UserProfileDTO> profileMap = new HashMap<>();
        for (UserProfileDTO userProfileDTO : userProfileDTOS) {
            profileMap.put(userProfileDTO.getId(), userProfileDTO);
        }

        for (Long id : ids) {
            UserProfileDTO profile = profileMap.get(id);
            VerifyProfile verifyProfile = verifyProfileMap.get(id);
            if (profile != null && verifyProfile != null) {
                result.getRows().add(entityToVerifyProfileDTO(verifyProfile, profile));
            }
        }
        return result;
    }


    /**
     * Get one talent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public VerifyProfileDTO findOne(Long id) {
        log.debug("Request to get VerifyProfile : {}", id);


        VerifyProfile verifyProfile = getVerifyProfile(id);
        if (verifyProfile == null) {
            return null;
        }

        UserProfileDTO userProfileDTO = userProfileFeignClient.findOne(verifyProfile.getId());
        if (userProfileDTO == null) {
            return null;
        }

        return entityToVerifyProfileDTO(verifyProfile, userProfileDTO);
    }

    @Override
    public Map<Long, VerifyProfileDTO> findVerifyProfileByIds(Set<Long> uids) {
        if (CollectionUtil.isEmpty(uids)) {
            return null;
        }
        Map<Long,VerifyProfileDTO>returnMap=new HashMap<>();
        uids.forEach(id -> {
            VerifyProfileDTO verifyProfileDTO = findOne(id);
            if(verifyProfileDTO!=null){
                returnMap.put(id,verifyProfileDTO);
            }
        });
        return returnMap;
    }

    private VerifyProfile getVerifyProfile(Long id) {
        VerifyProfile verifyProfile = redisVerifyProfileRepository.findOne(id);

        if (verifyProfile == null) {
            verifyProfile = verifyProfileRepository.findOne(id);
            if (verifyProfile != null) {
                redisVerifyProfileRepository.save(verifyProfile);
            }
        }

        if (verifyProfile == null) {
            return null;
        }

        return verifyProfile;
    }

    private VerifyProfileDTO entityToVerifyProfileDTO(VerifyProfile verifyProfile, UserProfileDTO userProfileDTO) {
        VerifyProfileDTO dto = VerifyProfileMapper.MAPPER.toVerifyProfileDTO(verifyProfile);
        dto.setNick(userProfileDTO.getNick());
        dto.setIcon(userProfileDTO.getIcon());
        return dto;
    }

    /**
     * Delete the  talent by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VerifyProfile : {}", id);
        VerifyProfile verifyProfile = getVerifyProfile(id);
        if (verifyProfile != null) {
            redisVerifyProfileRepository.delete(id);
            verifyProfileRepository.delete(id);
            redisVerifyProfileSetRepository.removeId(verifyProfile.getVerifyType(), id);
        }
    }
}
