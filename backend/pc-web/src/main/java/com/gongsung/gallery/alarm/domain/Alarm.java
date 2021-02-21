package com.gongsung.gallery.alarm.domain;


import lombok.Data;

@Data
public class Alarm {

    private MessageType type;
    private String content;
    private Long targetUserId;

}
