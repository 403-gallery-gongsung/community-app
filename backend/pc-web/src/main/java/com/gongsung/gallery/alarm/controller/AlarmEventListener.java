package com.gongsung.gallery.alarm.controller;

import com.gongsung.gallery.alarm.domain.Alarm;
import com.gongsung.gallery.alarm.domain.MessageType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


@Component
@RequiredArgsConstructor
public class AlarmEventListener {

    private static final Logger logger = LoggerFactory.getLogger(AlarmEventListener.class);
    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//
//        String username = (String) headerAccessor.getSessionAttributes().get("username");
//        if(username != null) {
//            logger.info("User Disconnected : " + username);
//
//            Alarm chatMessage = new Alarm();
//            chatMessage.setType(MessageType.COMMENT);
//           // chatMessage.setTargetUserId(username);
//
//            messagingTemplate.convertAndSend("/topic/public", chatMessage);
//        }
    }
}
