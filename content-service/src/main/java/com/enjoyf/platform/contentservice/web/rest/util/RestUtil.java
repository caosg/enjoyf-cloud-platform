package com.enjoyf.platform.contentservice.web.rest.util;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pengxu on 2017/5/26.
 */
@Component
public class RestUtil {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * @param URL         case:http://api.joyme.com/test?id={id}&appkey={appkey}
     * @param uriParamMap map.put("id",id); map.put("appkey",appkey);
     * @return
     */
    public String query(String URL, Map<String, Object> uriParamMap) {
        return restTemplate().getForObject(URL, String.class, uriParamMap);
    }

}
