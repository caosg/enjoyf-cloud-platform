package com.enjoyf.platform.profileservice.service.impl;

import com.enjoyf.platform.profileservice.service.contentservice.dto.UserCommentSumDTO;
import com.enjoyf.platform.page.ScoreRange;
import com.enjoyf.platform.page.ScoreRangeRows;
import com.enjoyf.platform.profileservice.domain.VerifyProfile;
import com.enjoyf.platform.profileservice.service.contentservice.UserCommentSumFeignClient;
import com.enjoyf.platform.profileservice.service.userservice.UserProfileFeignClient;
import com.enjoyf.platform.profileservice.service.userservice.dto.UserProfileDTO;
import com.enjoyf.platform.profileservice.repository.jpa.VerifyProfileRepository;
import com.enjoyf.platform.profileservice.repository.redis.RedisVerifyProfileRepository;
import com.enjoyf.platform.profileservice.repository.redis.RedisWikiAppRecommendUserRepository;
import com.enjoyf.platform.profileservice.service.WikiAppProfileService;
import com.enjoyf.platform.profileservice.service.dto.WikiAppProfileDTO;
import com.google.common.base.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ericliu on 2017/6/24.
 */
@Service
public class WikiAppProfileServiceImpl implements WikiAppProfileService {

    private final VerifyProfileRepository verifyProfileRepository;


    private final RedisVerifyProfileRepository redisVerifyProfileRepository;

    private final UserProfileFeignClient userProfileFeignClient;

    private final UserCommentSumFeignClient userCommentSumFeignClient;

    private final RedisWikiAppRecommendUserRepository redisWikiAppRecommendUserRepository;

    public WikiAppProfileServiceImpl(VerifyProfileRepository verifyProfileRepository,
                                     RedisVerifyProfileRepository redisVerifyProfileRepository,
                                     UserProfileFeignClient userProfileFeignClient,
                                     UserCommentSumFeignClient userCommentSumFeignClient,
                                     RedisWikiAppRecommendUserRepository redisWikiAppRecommendUserRepository) {
        this.verifyProfileRepository = verifyProfileRepository;
        this.redisVerifyProfileRepository = redisVerifyProfileRepository;
        this.userProfileFeignClient = userProfileFeignClient;
        this.userCommentSumFeignClient = userCommentSumFeignClient;
        this.redisWikiAppRecommendUserRepository = redisWikiAppRecommendUserRepository;
    }

    @Override
    public WikiAppProfileDTO findOne(Long uid) {
        UserProfileDTO userProfileDTO = userProfileFeignClient.findOne(uid);
        VerifyProfile verifyProfile = getVerifyProfile(uid);
        UserCommentSumDTO userCommentSumDTO = userCommentSumFeignClient.findOne(uid);

        WikiAppProfileDTO dto = null;
        if (userProfileDTO != null) {
            dto = toDTO(userProfileDTO, verifyProfile, userCommentSumDTO);
        }

        return dto;
    }

    @Override
    public void saveRecommendUser(Long uid) {
        redisWikiAppRecommendUserRepository.insertRecommendUser(uid);
    }

    @Override
    public boolean deleteRecommendUser(Long uid) {
        return redisWikiAppRecommendUserRepository.delRecommendUser(uid);
    }

    @Override
    public ScoreRangeRows<WikiAppProfileDTO> findAllRecommendUser(ScoreRange scoreRange) {
        List<Long> uidList = redisWikiAppRecommendUserRepository.findAll(scoreRange);
        ScoreRangeRows<WikiAppProfileDTO> result = new ScoreRangeRows<>();
        result.setRange(scoreRange);
        result.getRows().addAll(buildDtoList(uidList));

        return result;
    }


    @Override
    public Page<WikiAppProfileDTO> findAllRecommendUser(Pageable pageable) {
        Page<Long> page = redisWikiAppRecommendUserRepository.findAll(pageable);

        List<Long> uidList = page.getContent();

        Page<WikiAppProfileDTO> result = new PageImpl<>(buildDtoList(uidList), pageable, page.getTotalElements());
        return result;
    }

    private List<WikiAppProfileDTO> buildDtoList(List<Long> uidList) {

        Long[] uidArray = new Long[]{};
        uidArray = uidList.toArray(uidArray);

        List<UserProfileDTO> userProfileDTOS = userProfileFeignClient.findUserProfilesByUids(uidArray);
        Map<Long, UserProfileDTO> userProfileMap = new HashMap<>();
        userProfileDTOS.stream().forEach(userProfile -> {
            userProfileMap.put(userProfile.getId(), userProfile);
        });
        Map<Long, VerifyProfile> verifyProfileMap = findAllUids(uidArray);
        Map<Long, UserCommentSumDTO> userCommentSumList = userCommentSumFeignClient.findByIds(uidArray);

        List<WikiAppProfileDTO> result = new ArrayList<>();
        for (Long uid : uidList) {
            UserProfileDTO userProfileDTO = userProfileMap.get(uid);
            if (userProfileDTO != null) {
                WikiAppProfileDTO dto = toDTO(userProfileDTO,
                    verifyProfileMap.get(uid),
                    userCommentSumList.get(uid));
                if (dto != null) {
                    result.add(dto);
                }
            }
        }
        return result;
    }

    private WikiAppProfileDTO toDTO(UserProfileDTO userProfileDTO,
                                    VerifyProfile verifyProfile,
                                    UserCommentSumDTO userCommentSumDTO) {
        WikiAppProfileDTO dto = new WikiAppProfileDTO();
        dto.setUid(userProfileDTO.getId());
        if (userCommentSumDTO != null) {
            dto.setCommentSum(userCommentSumDTO.getCommentSum());
            dto.setUserfulSum(userCommentSumDTO.getUsefulSum());
        }
        dto.setSex(userProfileDTO.getSex() == null ? -1 : userProfileDTO.getSex());
        dto.setNick(userProfileDTO.getNick());
        dto.setIcon(userProfileDTO.getIcon());
        dto.setDescription(verifyProfile != null ? verifyProfile.getVerifyInfo() : (Strings.isNullOrEmpty(userProfileDTO.getDiscription()) ? "" : userProfileDTO.getDiscription()));
        if (verifyProfile != null) {
            dto.setVerifyProfileType(verifyProfile.getVerifyType());
        }
        dto.setIcon(userProfileDTO.getIcon());
        return dto;
    }

    private Map<Long, VerifyProfile> findAllUids(Long... uids) {
        Map<Long, VerifyProfile> result = new HashMap<>();
        for (Long uid : uids) {
            VerifyProfile verifyProfile = getVerifyProfileByCache(uid);

            if (verifyProfile != null) {
                result.put(uid, verifyProfile);
            }
        }
        return result;
    }

    private VerifyProfile getVerifyProfileByCache(Long id) {
        return redisVerifyProfileRepository.findOne(id);
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

}
