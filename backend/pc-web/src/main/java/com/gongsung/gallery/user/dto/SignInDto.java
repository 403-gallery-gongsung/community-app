package com.gongsung.gallery.user.dto;

import com.gongsung.gallery.User;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class SignInDto {
  @NotBlank @NotEmpty
  private String email;

  @NotBlank @NotEmpty
  private String password;
  private boolean remember;

  @Builder
  public SignInDto(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public User toEntity() {
    return User.builder()
        .email(this.email)
        .password(this.password)
        .build();
  }
}
