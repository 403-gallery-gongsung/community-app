package com.gongsung.gallery.user.service;

import com.gongsung.gallery.User;
import com.gongsung.gallery.user.form.*;
import com.gongsung.gallery.user.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JavaMailSender javaMailSender;

  public Long count() {
    return userRepository.count();
  }

  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  public User findByNickName(String nickName) {
    return userRepository.findByNickName(nickName);
  }

  public boolean existsByNickName(String nickName) {
    return userRepository.existsByNickName(nickName);
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }


  private User save(SignUpForm signUpForm) {
    User user = User.builder()
        .email(signUpForm.getEmail())
        .nickname(signUpForm.getNickName())
        .password(passwordEncoder.encode(signUpForm.getPassWord()))
        .emailCheckToken(String.valueOf(UUID.randomUUID()))
        .build();

    userRepository.save(user);

    return user;
  }

  @Transactional
  public User createNewUser(SignUpForm signUpForm) {
    User newUser = save(signUpForm);
    sendSignUpConfirmEmail(newUser);

    return newUser;
  }

  private void sendSignUpConfirmEmail(User newUser) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(newUser.getEmail());
    mailMessage.setSubject("[gallery]이메일 회원 가입 인증");
    mailMessage.setText("/check-email-token?token="
        + newUser.getEmailCheckToken() + "&email=" + newUser.getEmail());

    javaMailSender.send(mailMessage);
  }

  public void login(User user) {
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
        user.getNickname(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER"))
    );
    SecurityContextHolder.getContext().setAuthentication(token);
  }
}
