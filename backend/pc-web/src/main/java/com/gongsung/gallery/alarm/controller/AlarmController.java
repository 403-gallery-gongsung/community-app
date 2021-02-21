package com.gongsung.gallery.alarm.controller;


import com.gongsung.gallery.alarm.domain.Alarm;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class AlarmController {

    @MessageMapping("/board/{id}")
    @SendTo("/user/{id}")
    public Alarm sendMessage(@DestinationVariable("id") Long userId, @Payload Alarm alarm) {
        return alarm;
    }

}
