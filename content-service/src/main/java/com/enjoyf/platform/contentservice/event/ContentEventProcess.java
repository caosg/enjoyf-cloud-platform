package com.enjoyf.platform.contentservice.event;

import com.enjoyf.platform.contentservice.config.WebAppConfig;
import com.enjoyf.platform.contentservice.web.rest.util.AskUtil;
import com.enjoyf.platform.contentservice.web.rest.util.RestUtil;
import com.enjoyf.platform.event.Event;
import com.enjoyf.platform.event.EventConstants;
import com.enjoyf.platform.event.EventReceiver;
import com.enjoyf.platform.event.EventSender;
import com.enjoyf.platform.event.content.ContentEventConstants;
import com.enjoyf.platform.event.content.ContentSolrDeleteEvent;
import com.enjoyf.platform.event.content.ContentSolrEvent;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ericliu on 2017/5/9.
 */
@Component
public class ContentEventProcess implements EventSender, EventReceiver {
    private static Logger log = LoggerFactory.getLogger(ContentEventProcess.class);

    private final AmqpAdmin amqpAdmin;
    private final RabbitTemplate amqpTemplate;


    @Autowired
    private WebAppConfig webAppConfig;

    @Autowired
    private RestUtil restUtil;

    @Autowired
    public ContentEventProcess(AmqpAdmin amqpAdmin, RabbitTemplate amqpTemplate) {
        this.amqpAdmin = amqpAdmin;
        this.amqpTemplate = amqpTemplate;

        declareContent();
        this.amqpTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }

    @Override
    public void send(Event event) {
        amqpTemplate.setExchange(EventConstants.EVENT_EXCHANGE);
        amqpTemplate.setRoutingKey(event.getBindKey());
        amqpTemplate.convertAndSend(event);
    }

    @Override
    public void receiveEvent(Event event) {
        log.info("========receiveEvent: {} ", event);

        //wikiapp文章搜索
        if (event instanceof ContentSolrEvent) {

            ContentSolrEvent solrEvent = (ContentSolrEvent) event;
            JSONObject object = new JSONObject();
            object.put("id", solrEvent.getId());
            object.put("entryid", solrEvent.getEntryid());
            object.put("type", solrEvent.getType());
            object.put("title", solrEvent.getTitle());
            object.put("createtime", solrEvent.getCreatetime());

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("c", "wikiapp");
            paramMap.put("field", object.toString());

            String responseBody = restUtil.query(webAppConfig.getSolr_save() + "?c={c}&field={field}", paramMap);
            log.info("========responseBody: {} ", responseBody);
        } else if (event instanceof ContentSolrDeleteEvent) {
            ContentSolrDeleteEvent deleteEvent = (ContentSolrDeleteEvent) event;


            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("c", "wikiapp");
            paramMap.put("q", "(entryid:" + AskUtil.getWikiappSearchEntryId(String.valueOf(deleteEvent.getId()), deleteEvent.getType()) + ")");

            String responseBody = restUtil.query(webAppConfig.getSolr_delete() + "?c={c}&q={q}", paramMap);
            log.info("========responseBody: {} ", responseBody);
        } else {

        }


    }

    private void declareContent() {
        Exchange e = ExchangeBuilder.topicExchange(EventConstants.EVENT_EXCHANGE).build();
        Queue q = new Queue(ContentEventConstants.queueName);
        Binding bind = BindingBuilder.bind(q).to(e).with(ContentEventConstants.bindKey).noargs();

        amqpAdmin.declareQueue(q);
        amqpAdmin.declareExchange(e);
        amqpAdmin.declareBinding(bind);
    }


    @RabbitListener(queues = ContentEventConstants.queueName)
    public void receiveContentMessage(Message message) {
        Event event = (Event) amqpTemplate.getMessageConverter().fromMessage(message);
        log.info("========receiveEvent: {} , message routing keys: {}", message, message.getMessageProperties().getReceivedRoutingKey());
        receiveEvent(event);
    }


}
