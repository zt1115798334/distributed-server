package com.example.distributedcommoncache.service.impl;

import com.example.distributedcommoncache.service.RedisScriptService;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2021/1/18 17:11
 * description:
 */
@AllArgsConstructor
@Component
public class RedisScriptServiceImpl implements RedisScriptService {

    private final StringRedisTemplate stringRedisTemplate;

    private final DefaultRedisScript<Boolean> identityRedisScript;

    @Override
    public String getIdentity(String key, String accountUeId) {
        List<String> keys = Arrays.asList(key, accountUeId);
        Boolean execute = stringRedisTemplate.execute(identityRedisScript, keys, "100");
        System.out.println("execute = " + execute);
        return String.valueOf(execute);
    }
}
