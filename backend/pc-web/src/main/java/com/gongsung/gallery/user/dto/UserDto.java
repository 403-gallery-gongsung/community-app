package com.gongsung.gallery.user.dto;

import com.gongsung.gallery.Board;
import com.gongsung.gallery.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

  private Long id;
  private String email;
  private String password;
  private String nickname;
  private String birthdate;
  private String bio; // 자기소개
  private String occupation; // 직업
  private String location; // varchar(255)
  private String profileUrl;
  private LocalDateTime joinedAt;
  private String emailCheckToken;

  private List<Board> boards;

  @Builder
  public UserDto(Long id, String email, String password, String nickname, String birthdate,
      String bio, String occupation, String location, String profileUrl,
      LocalDateTime joinedAt, String emailCheckToken,
      List<Board> boards) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.birthdate = birthdate;
    this.bio = bio;
    this.occupation = occupation;
    this.location = location;
    this.profileUrl = profileUrl;
    this.joinedAt = joinedAt;
    this.emailCheckToken = emailCheckToken;
    this.boards = boards;
  }

  public User toEntity() {
    return User.builder()
        .email(this.email)
        .nickname(this.nickname)
        .password(this.password)
        .emailCheckToken(this.emailCheckToken)
        .build();
  }
}
