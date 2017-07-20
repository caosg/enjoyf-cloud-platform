package com.enjoyf.platform.messageservice.service.impl;

import com.enjoyf.platform.messageservice.domain.PushProfileDevice;
import com.enjoyf.platform.messageservice.repository.jpa.PushProfileDeviceRepository;
import com.enjoyf.platform.messageservice.repository.redis.RedisDeviceRepository;
import com.enjoyf.platform.messageservice.service.PushProfileDeviceService;
import com.enjoyf.platform.messageservice.service.dto.PushProfileDeviceDTO;
import com.enjoyf.platform.messageservice.service.mapper.PushProfileDeviceMapper;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 用户中心服务门面类，复杂业务接口聚合
 * Created by shuguangcao on 2017/3/27.
 */
@Service
public class PushProfileDeviceServiceImpl implements PushProfileDeviceService {

    private final static Logger log = LoggerFactory.getLogger(PushProfileDeviceServiceImpl.class);

    private final RedisDeviceRepository redisDeviceRepository;
    private final PushProfileDeviceRepository pushProfileDeviceRepository;

    public PushProfileDeviceServiceImpl(RedisDeviceRepository redisDeviceRepository,
                                        PushProfileDeviceRepository pushProfileDeviceRepository) {
        this.redisDeviceRepository = redisDeviceRepository;
        this.pushProfileDeviceRepository = pushProfileDeviceRepository;
    }


    @Override
    public PushProfileDeviceDTO savePushProfileDevice(PushProfileDeviceDTO dto) {
        redisDeviceRepository.setDevice(dto.getAppkey(), dto.getUid(), dto.getDeviceid() + ":" + dto.getPlatform());
        PushProfileDevice profileDevice = PushProfileDeviceMapper.MAPPER.toEntity(dto);
        PushProfileDevice deviceByDB = pushProfileDeviceRepository.findOneByUidAndAppkey(dto.getUid(), dto.getAppkey());

        if (deviceByDB != null) {
            profileDevice.setId(deviceByDB.getId());
        }
        pushProfileDeviceRepository.save(profileDevice);
        return PushProfileDeviceMapper.MAPPER.toDTO(profileDevice);
    }

    @Override
    public void deletePushProfileDevice(String appkey, Long uid) {
        redisDeviceRepository.delDevice(appkey, uid);
        PushProfileDevice pushProfileDevice = pushProfileDeviceRepository.findOneByUidAndAppkey(uid, appkey);

        if (pushProfileDevice != null) {
            pushProfileDeviceRepository.delete(pushProfileDevice);
        }
    }

    @Override
    public String[] getPushProfileDevice(String appkey, Long uid) {
        String deviceId = redisDeviceRepository.getDevice(appkey, uid);
        if (Strings.isNullOrEmpty(deviceId)) {
            return null;
        }

        return deviceId.split(":");
    }
}
