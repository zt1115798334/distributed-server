package com.example.distributedcommoncache.controller;

import com.example.distributedcommoncache.service.RedisDatabase;
import com.example.distributedcommoncache.service.RedisScriptService;
import com.example.distributedcommoncache.service.StringRedisService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2021/1/18 17:13
 * description:
 */
@AllArgsConstructor
@RestController
public class RedisController {

    private final RedisScriptService redisScriptService;

    private final StringRedisService stringRedisService;

    @GetMapping("getIdentity")
    public void getIdentity(@RequestParam String username) {
        System.out.println("username = " + username);
        String identity = redisScriptService.getIdentity("identity", username);
        System.out.println("identity = " + identity);
    }

    @GetMapping("setNotContainExpire")
    public void setNotContainExpire() {
        stringRedisService.setNotContainExpire(RedisDatabase.REDIS_ZERO, "dddd", "ddddd");
    }
}
