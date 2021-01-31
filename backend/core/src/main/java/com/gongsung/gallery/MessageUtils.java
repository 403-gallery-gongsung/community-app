package com.gongsung.gallery;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageUtils {
  private MessageSource messageSource = null;

  MessageUtils(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  /*
  public String getMessageUtils(String args) {
    return messageSource.getMessage(args, Locale.getDefault());
  }
  */

  public String getMessageUtils(String args, Object[] objs) {
    return messageSource.getMessage(args, objs, Locale.getDefault());
  }
}
