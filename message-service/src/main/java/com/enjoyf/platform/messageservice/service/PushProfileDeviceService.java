package com.enjoyf.platform.messageservice.service;

import com.enjoyf.platform.messageservice.service.dto.PushProfileDeviceDTO;

/**
 * Service Interface for managing UserAccount.
 */
public interface PushProfileDeviceService {

    PushProfileDeviceDTO savePushProfileDevice(PushProfileDeviceDTO pushProfileDeviceDTO);

    void deletePushProfileDevice(String appkey, Long uid);

    String[] getPushProfileDevice(String appkey, Long uid);

}
