package com.enjoyf.platform.contentservice.web.rest;

import com.enjoyf.platform.contentservice.event.ContentEventProcess;
import com.enjoyf.platform.event.content.ContentInsertEvent;
import com.enjoyf.platform.event.content.ContentSolrEvent;
import com.enjoyf.platform.event.user.UserRegisterEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Resource to return information about the currently running Spring profiles.
 */
@RestController
@RequestMapping("/api/content-event")
public class ConetentEventResource {


    private final ContentEventProcess contentEventProcess;


    public ConetentEventResource(ContentEventProcess contentEventProcess) {
        this.contentEventProcess = contentEventProcess;
    }

    @GetMapping("/send")
    public String send() {
        UserRegisterEvent event = new UserRegisterEvent();
        event.setNick("ericliu");
        contentEventProcess.send(event);

        ContentInsertEvent insertEvent = new ContentInsertEvent();
        insertEvent.setTitle("this is test title");
        contentEventProcess.send(insertEvent);
        return "success";
    }


}
