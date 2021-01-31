package com.gongsung.gallery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MessageRunner implements ApplicationRunner {

  @Autowired
  MessageUtils messageUtils;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    System.out.println(messageUtils.getMessageUtils("hungry", new String[]{"I'm hungry","i want some bigmac"}));
  }

}
