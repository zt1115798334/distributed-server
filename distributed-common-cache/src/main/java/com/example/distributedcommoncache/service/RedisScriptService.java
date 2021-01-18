package com.example.distributedcommoncache.service;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2021/1/18 17:10
 * description:
 */
public interface RedisScriptService {

    String getIdentity(String key, String accountUeId);
}
