package com.example.distributedcommoncache.service;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2021/1/18 17:49
 * description:
 */
public interface RedisBloomFilterService {

    void insertBloomFilter(String key, String element, Date expireDate);

    boolean mayExist(String key, String element);
}
