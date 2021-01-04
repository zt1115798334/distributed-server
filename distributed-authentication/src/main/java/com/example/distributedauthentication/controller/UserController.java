package com.example.distributedauthentication.controller;

import com.example.distributedauthentication.entity.User;
import com.example.distributedauthentication.service.UserService;
import com.example.distributedauthentication.utils.DtoUserUtils;
import com.example.distributedcommon.base.BaseController;
import com.example.distributedcommon.base.ResultMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2020/12/21 17:41
 * description:
 */
@Slf4j
@AllArgsConstructor
@RequestMapping("api/user")
@RestController
public class UserController extends BaseController {

    private final UserService userService;

    @PostMapping("createdUser")
//    public ResultMessage saveBook(@RequestBody VoBook voBook) {
    public ResultMessage createdUser() {
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                String account = "account" + random.nextInt();
                String password = "123456";
                String userName = "userName" + random.nextInt();
                String phone = "phone" + random.nextInt();
                String email = "email" + random.nextInt() + "@qq.com";
                userService.createdUser(account, password, userName, phone, email);
            }).start();
        }
        return success();
    }

    @GetMapping("findAllUser")
    public ResultMessage findAllUser() {
        //for debug
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("authentication: " + authentication.getAuthorities().toString());
        List<User> userList = userService.findAllUser();
        return success(DtoUserUtils.changeUser(userList));
    }
}
