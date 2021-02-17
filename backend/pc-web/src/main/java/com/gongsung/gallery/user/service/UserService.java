package com.gongsung.gallery.user.service;

import com.gongsung.gallery.User;
import com.gongsung.gallery.user.dto.SignUpDto;
import com.gongsung.gallery.user.dto.UserDto;
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

  public User findById(Long id) { return userRepository.findById(id); }

  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public boolean existsByEmail(String email) throws Exception{
    return userRepository.existsByEmail(email);
  }

  public User findByNickName(String nickName) {
    return userRepository.findByNickName(nickName);
  }

  public boolean existsByNickName(String nickName) { return userRepository.existsByNickName(nickName); }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  private User save(SignUpDto signUpForm) {
    UserDto userDto = UserDto.builder().
        email(signUpForm.getEmail())
        .nickname(signUpForm.getNickName())
        .password(getEncodePassword(signUpForm.getPassword()))
        .emailCheckToken(String.valueOf(UUID.randomUUID()))
        .build();

    userRepository.save(userDto.toEntity());
    return userDto.toEntity();
  }

  public String getEncodePassword(String password) {
    return passwordEncoder.encode(password);
  }


  @Transactional
  public User createNewUser(SignUpDto signUpDto) {
    User newUser = this.save(signUpDto);
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
        user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));

    SecurityContextHolder.getContext().setAuthentication(token);
  }

  public boolean passwordEquals(String email,String password) {
    User user = this.findByEmail(email);
    if (passwordEncoder.matches(password,user.getPassword())) {
      return true;
    }

    return false;
  }
}
