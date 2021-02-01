package com.gongsung.gallery.utils;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageUtils {
    //private static MessageSourceAccessor msAcc = null;
    private MessageSource messageSource;

    MessageUtils(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    //public String getMessage(String code) {
    //    return messageSource.getMessage(code, Locale.getDefault());
    //}

    public String getMessage(String code, Object[] objs) {
        return messageSource.getMessage(code, objs, Locale.getDefault());
    }
}