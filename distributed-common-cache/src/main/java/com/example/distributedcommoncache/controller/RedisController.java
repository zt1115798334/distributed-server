package com.example.distributedcommoncache.controller;

import com.example.distributedcommoncache.bloomFilter.RedisBloomFilter;
import com.example.distributedcommoncache.service.RedisBloomFilterService;
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

    private final RedisBloomFilterService redisBloomFilterService;

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

    @GetMapping("testRedisBloomFilter")
    public void testRedisBloomFilter() {
        //大概3百万数据，误差率在10%作用。
        redisBloomFilterService.insertBloomFilter("topic_read:20200812", "76930242", RedisBloomFilter.getTwelveTime());
        redisBloomFilterService.insertBloomFilter("topic_read:20200812", "76930243", RedisBloomFilter.getTwelveTime());
        redisBloomFilterService.insertBloomFilter("topic_read:20200812", "76930244", RedisBloomFilter.getTwelveTime());
        redisBloomFilterService.insertBloomFilter("topic_read:20200812", "76930245", RedisBloomFilter.getTwelveTime());
        redisBloomFilterService.insertBloomFilter("topic_read:20200812", "76930246", RedisBloomFilter.getTwelveTime());

        System.out.println(redisBloomFilterService.mayExist("topic_read:20200812", "76930242"));
        System.out.println(redisBloomFilterService.mayExist("topic_read:20200812", "76930244"));
        System.out.println(redisBloomFilterService.mayExist("topic_read:20200812", "76930246"));
        System.out.println(redisBloomFilterService.mayExist("topic_read:20200812", "76930248"));
        System.out.println(redisBloomFilterService.mayExist("topic_read:20200812", "769302428"));

    }
}
