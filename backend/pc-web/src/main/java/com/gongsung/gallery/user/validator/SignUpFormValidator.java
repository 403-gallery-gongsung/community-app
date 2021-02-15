package com.gongsung.gallery.user.validator;

import com.gongsung.gallery.user.form.*;
import com.gongsung.gallery.user.repository.UserRepository;
import com.gongsung.gallery.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

  private final UserService userService;

  @Override
  public boolean supports(Class<?> clazz) {
    return clazz.isAssignableFrom(SignUpForm.class);
  }

  @Override
  public void validate(Object target, Errors errors) {
    SignUpForm signUpForm = (SignUpForm)target;
    if (userService.existsByEmail(signUpForm.getEmail())) {
      errors.rejectValue("email","invalid.email",new Object[]{signUpForm.getEmail()},"이미 사용중인 Email입니다.");
    }

    if (userService.existsByNickName(signUpForm.getNickName())) {
      errors.rejectValue("nickName","invalid.nickName",new Object[]{signUpForm.getNickName()},"이미 사용중인 Nickname입니다.");
    }
  }
}
