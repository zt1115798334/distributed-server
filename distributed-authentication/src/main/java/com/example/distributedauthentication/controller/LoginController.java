package com.example.distributedauthentication.controller;

import com.example.distributedauthentication.bo.ResponseUserToken;
import com.example.distributedauthentication.service.LoginService;
import com.example.distributedcommon.base.BaseController;
import com.example.distributedcommon.base.ResultMessage;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("api/login")
@RestController
public class LoginController extends BaseController {


    private final LoginService loginService;

    @PostMapping("login")
    public ResultMessage login(@RequestParam String account, @RequestParam String password) {
        ResponseUserToken response = loginService.login(account, password);
        return success(response);
    }
}
