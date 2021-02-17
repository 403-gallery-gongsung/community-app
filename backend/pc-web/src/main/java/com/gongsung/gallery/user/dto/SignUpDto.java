package com.gongsung.gallery.user.dto;

import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter @Setter
public class SignUpDto {

  @NotBlank @Length(min = 3, max = 20)
  @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9_-]{3,20}$")
  private String nickName;

  @NotBlank @Email
//  @Pattern(regexp = "")
  private String email;

  @NotBlank @Length(min = 8, max = 20)
  private String password;
}
