package com.lxz.user.vo.login;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginDto {

    @NotNull(message = "用户名不能为空")
    private String userName;

    @NotNull(message = "密码不能为空")
    private String password;
}
