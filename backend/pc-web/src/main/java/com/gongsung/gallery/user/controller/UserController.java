package com.gongsung.gallery.user.controller;

import com.gongsung.gallery.User;
import com.gongsung.gallery.user.service.UserService;
import java.time.LocalDateTime;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import com.gongsung.gallery.user.dto.*;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final Validator signUpFormValidator;
    private final UserService userService;

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId != null) {
            model.addAttribute("sessionId", userId);
        }

        return "index";
    }

    @GetMapping("/signUp")
    public String signUpForm(Model model) {
        model.addAttribute(new SignUpDto());

        return "account/signUp";
    }

    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    @PostMapping("/signUp")
    public String signUpSubmit(@Valid SignUpDto signUpDto, Model model, Errors errors) {
        if (errors.hasErrors()) {
            return "account/signUp";
        }

        User user = userService.createNewUser(signUpDto);
//    위에서 user에 대한 id를 반환했으면, id에 대해 repository.findById()를 통해 user를 반환받아 로그인 시키면 될 것 같은데.
//    또한 아래의 login method에서도 session유지 부분을 염두..? 최근에는 JWT를 사용해서 연결한다고 함. 이것도 알아보자ㅋㅋ
//        userService.login(user);

        model.addAttribute("error", errors.hasErrors());
        model.addAttribute("numberOfUser", userService.count());
        model.addAttribute("nickName", user.getNickname());

        return "account/checkEmail";
    }

    @GetMapping("signIn")
    public String signInForm(Model model, SignInDto signInDto,
        @CookieValue(value = "signInCookie", required = false) Cookie cookie) {
        if (cookie != null) {
            signInDto.setEmail(cookie.getValue());
            signInDto.setRemember(true);
        }

        model.addAttribute(new SignInDto());

        return "account/signIn";
    }


    @PostMapping("signIn")
    public String signInSubmit(@Valid SignInDto signInDto,
        HttpSession session, HttpServletResponse response, Model model) {
        if (userService.passwordEquals(signInDto.getEmail(), signInDto.getPassword())) {
            if (signInDto.isRemember()) {
                Cookie cookie = new Cookie("signInCookie", signInDto.getEmail());
                cookie.setPath("/");
                cookie.setMaxAge(60 * 60 * 24 * 7);
                response.addCookie(cookie);
            }

            User user = userService.findByEmail(signInDto.getEmail());
            session.setAttribute("userId", user.getId());
//      session.setAttribute("user_id", userService.findByEmail(signInDto.getEmail()).getId());
            session.setMaxInactiveInterval(60 * 10);

            userService.login(signInDto.toEntity());
        }

        model.addAttribute("in", "로그인 됐지롱");
        return "redirect:/";
    }


    @GetMapping("signOut")
    public String signOutSubmit(HttpSession session, Model model) {
        session.invalidate();

        model.addAttribute("out", "로그아웃 됐지롱");

        return "redirect:/";
    }


    @GetMapping("/checkEmailToken")
    public String checkEmailToken(Model model, String token, String email) throws Exception {
        User user = userService.findByEmail(email);
        String view = "account/checkEmail";
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
