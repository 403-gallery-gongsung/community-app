package com.gongsung.gallery.controller;

import com.gongsung.gallery.
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @Autowired
    private MessageUtils messageUtils;

    @GetMapping("/msg")
    public String testMethod(){
        System.out.println(messageUtils.getMessage("error.common", new String[]{}));
        log.info("error.common: " + messageUtils.getMessage("error.common", new String[]{}));
        log.info("error.minlength: " + messageUtils.getMessage("error.minlength", new String[]{"1", "12"}));
        log.info("error.wrongReq: " + messageUtils.getMessage("error.wrongReq", new String[]{}));
        return "message";
    }
}
