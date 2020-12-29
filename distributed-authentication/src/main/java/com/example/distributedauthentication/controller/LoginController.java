package com.example.distributedauthentication.controller;

import com.example.distributedauthentication.bo.ResponseUserToken;
import com.example.distributedauthentication.service.LoginService;
import com.example.distributedcommon.base.BaseController;
import com.example.distributedcommon.base.ResultMessage;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("api/login")
@RestController
public class LoginController extends BaseController {


    private final LoginService loginService;

    @PostMapping("login")
    public ResultMessage login(@RequestParam String username, @RequestParam String password) {
        ResponseUserToken response = loginService.login(username, password);
        return success(response);
    }

    public static void main(String[] args) {
        String password = "admin";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
        String enPassword = encoder.encode(password);
        System.out.println(enPassword);
    }
}
