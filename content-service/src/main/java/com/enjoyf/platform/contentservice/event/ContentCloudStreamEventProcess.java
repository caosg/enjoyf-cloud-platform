//package com.enjoyf.platform.contentservice.event;
//
//import com.enjoyf.platform.event.Event;
//import com.enjoyf.platform.event.EventReceiver;
//import com.enjoyf.platform.event.EventSender;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.cloud.stream.messaging.Sink;
//import org.springframework.cloud.stream.messaging.Source;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Component;
//
///**
// * Created by ericliu on 2017/5/9.
// */
//@Deprecated
////@Component
////@EnableBinding({Sink.class,Source.class})
//public class ContentCloudStreamEventProcess implements EventSender,EventReceiver {
//    private static Logger log = LoggerFactory.getLogger(ContentCloudStreamEventProcess.class);
//
//    private final Sink sink;
//    private final Source source;
//
//    public ContentCloudStreamEventProcess(Sink sink, Source source) {
//        this.sink = sink;
//        this.source = source;
//    }
//
//    //发送事件
//    @Override
//    public void send(Event event) {
//      source.output().send(MessageBuilder.withPayload(event).
//          setHeader("contentType","application/json")
//          .setHeader("eventtype",event.getBindKey())
//          .build());
//    }
//
//    //接收事件
//    @Override
//    @StreamListener(target = Sink.INPUT,condition = "headers['eventtype']=='bind.event.contentservice.#'")
//    public void receiveEvent(Event event) {
//        log.info("========receiveEvent: {} ", event);
//    }
//}
