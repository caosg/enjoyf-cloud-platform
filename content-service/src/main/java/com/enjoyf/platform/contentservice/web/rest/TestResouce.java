package com.enjoyf.platform.contentservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ericliu on 2017/6/22.
 */
@RestController
@RequestMapping("/api/test/content")
public class TestResouce {

    @GetMapping("/info")
    @Timed
    public String createUserCollect(HttpServletRequest request){
       return "test info crof";
    }
}
