package com.enjoyf.platform.messageservice.service;

import com.enjoyf.platform.messageservice.domain.PushApp;
import com.enjoyf.platform.messageservice.service.dto.PushMessageDTO;
import com.enjoyf.platform.messageservice.service.exception.MessageException;

/**
 * Service Interface for managing UserAccount.
 */
public interface PushMessageService {

    void pushMessage(PushApp pushApp, PushMessageDTO pushMessageDTO) throws MessageException;
}

