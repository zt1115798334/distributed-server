package com.example.distributedcommoncache.service.impl;

import com.example.distributedcommoncache.bloomFilter.RedisBloomFilter;
import com.example.distributedcommoncache.service.RedisBloomFilterService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2021/1/18 17:49
 * description:
 */
@Component
public class RedisBloomFilterServiceImpl implements RedisBloomFilterService {

    RedisBloomFilter redisBloomFilter;

    public RedisBloomFilterServiceImpl(StringRedisTemplate stringRedisTemplate) {
        redisBloomFilter = new RedisBloomFilter(stringRedisTemplate,300000000, 0.1);
    }

    @Override
    public void insertBloomFilter(String key, String element, Date expireDate) {
        redisBloomFilter.insert(key, element, RedisBloomFilter.getTwelveTime());
    }

    @Override
    public boolean mayExist(String key, String element) {
        return redisBloomFilter.mayExist(key, element);
    }

}
