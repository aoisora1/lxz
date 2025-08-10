package com.lxz.user.controller.login;

import com.lxz.user.vo.login.LoginDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/login")
@Validated
public class LoginController {

    @PostMapping("/login")
    public void login(@RequestBody @Validated LoginDto loginDto) {
        log.info(loginDto.getPassword());
        System.out.println("xxxxxxxxxxx");
    }

    @PostMapping("/logout")
    public void logout() {

    }
}
