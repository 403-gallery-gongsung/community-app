package com.gongsung.gallery.user.controller;

import com.gongsung.gallery.User;
import com.gongsung.gallery.user.service.UserService;
import java.time.LocalDateTime;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;

import com.gongsung.gallery.user.form.*;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

  private final Validator signUpFormValidator;
  private final UserService userService;

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/sign-up")
  public String signUpForm(Model model) {
    model.addAttribute(new SignUpForm());

    return "account/sign-up";
  }

  @InitBinder("signUpForm")
  public void initBinder(WebDataBinder webDataBinder) {
    webDataBinder.addValidators(signUpFormValidator);
  }

  @PostMapping("/sign-up")
  public String signUpSubmit(@Valid SignUpForm signUpForm, Model model, Errors errors) {
    if (errors.hasErrors()) {
      return "account/sign-up";
    }

    User user = userService.createNewUser(signUpForm);
    userService.login(user);

    model.addAttribute("error", errors.hasErrors());
    model.addAttribute("numberOfUser", userService.count());
    model.addAttribute("nickName", user.getNickname());

    return "account/check-email";
  }

  @GetMapping("/check-email-token")
  public String checkEmailToken(Model model, String token, String email) {
    User user = userService.findByEmail(email);
    String view = "account/check-email";
    if (user == null) {
      model.addAttribute("error", "wrong.email");

      return view;
    }

    if (!user.getEmailCheckToken().equals(token)) {
      model.addAttribute("error", "wrong.token");

      return view;
    }

    user.setCreatedDate(LocalDateTime.now());
    userService.login(user);

    model.addAttribute("numberOfUser", userService.count());
    model.addAttribute("nickName", user.getNickname());

    return view;
  }
}
