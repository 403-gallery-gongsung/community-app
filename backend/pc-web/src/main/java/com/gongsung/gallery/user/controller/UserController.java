package com.gongsung.gallery.user.controller;

import com.gongsung.gallery.User;
import com.gongsung.gallery.user.service.UserService;
import java.time.LocalDateTime;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import com.gongsung.gallery.user.dto.*;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    model.addAttribute(new SignUpDto());

    return "account/sign-up";
  }

  @InitBinder("signUpForm")
  public void initBinder(WebDataBinder webDataBinder) {
    webDataBinder.addValidators(signUpFormValidator);
  }

  @PostMapping("/sign-up")
  public String signUpSubmit(@Valid SignUpDto signUpDto, Model model, Errors errors) {
    if (errors.hasErrors()) {
      return "account/sign-up";
    }

    User user = userService.createNewUser(signUpDto);
//    위에서 user에 대한 id를 반환했으면, id에 대해 repository.findById()를 통해 user를 반환받아 로그인 시키면 될 것 같은데.
//    또한 아래의 login method에서도 session유지 부분을 염두..? 최근에는 JWT를 사용해서 연결한다고 함. 이것도 알아보자ㅋㅋ
    userService.login(user);

    model.addAttribute("error", errors.hasErrors());
    model.addAttribute("numberOfUser", userService.count());
    model.addAttribute("nickName", user.getNickname());

    return "account/check-email";
  }

  @GetMapping("sign-in")
  public String signInForm(Model model, SignInDto signInDto,
      @CookieValue(value = "signIn", required = false) Cookie cookie) {
    if (cookie != null) {
      signInDto.setEmail(cookie.getValue());
      signInDto.setRemember(true);
    }

    model.addAttribute(new SignInDto());

    return "account/sign-in";
  }

  @PostMapping("sign-in")
  public String signInSubmit(@Valid SignInDto signInDto,
      HttpSession session,HttpServletResponse response, Model model) {
    if (userService.passwordEquals(signInDto.getEmail(), signInDto.getPassword())) {
      session.setAttribute("signInForm",signInDto);

      Cookie cookie = new Cookie("signIn", signInDto.getEmail());

      cookie.setPath("/");
      if (signInDto.isRemember()) {
        cookie.setMaxAge(60 * 60 * 24 * 7);
      } else {
        cookie.setMaxAge(0);
      }

      response.addCookie(cookie);
      userService.login(signInDto.toEntity());

    }

    model.addAttribute("in", "로그인 됐지롱");
    return "index";
//    return "redirect:/";
  }


  @GetMapping("sign-out")
  public String signOutSubmit(HttpSession session,Model model) {
    session.invalidate();

    model.addAttribute("out", "로그아웃 됐지롱");

    return "redirect:/";
  }


  @GetMapping("/check-email-token")
  public String checkEmailToken(Model model, String token, String email) throws Exception {
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
