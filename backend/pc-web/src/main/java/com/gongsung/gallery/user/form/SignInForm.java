package com.gongsung.gallery.user.form;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInForm {
  @NotBlank
  private String email;

  @NotBlank
  private String passWord;
}
