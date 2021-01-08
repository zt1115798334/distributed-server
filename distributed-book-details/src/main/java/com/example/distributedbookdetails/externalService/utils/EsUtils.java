package com.example.distributedbookdetails.externalService.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Objects;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2018/9/10 15:45
 * description: es相关工具类
 */
@SuppressWarnings("SameReturnValue")
@Slf4j
public class EsUtils {


    public static final String getEsObjectEmpty = "{\"msg\":\"操作成功\",\"result\":\"{}\",\"code\":\"0\"}";


    public static final String getEsArraysEmpty = "{\"msg\":\"操作成功\",\"result\":\"[]\",\"code\":\"0\"}";

    public static Optional<JSONArray> getJSONArrayEsResult(String str) {
        return getEsState(str)
                .filter(jo -> jo.containsKey("result") && jo.getJSONArray("result") != null)
                .map(jo -> jo.getJSONArray("result"));
    }

    public static Optional<JSONObject> getJSONObjectEsResult(String str) {
        return getEsState(str)
                .filter(jo -> jo.containsKey("result") && jo.getJSONObject("result") != null)
                .map(jo -> jo.getJSONObject("result"));
    }

    public static Optional<JSONObject> getEsState(String str) {
        return Optional.ofNullable(str)
                .map(JSONObject::parseObject)
                .filter(jo -> {
                    if (Objects.equal("0", jo.getString("code"))) {
                        return Boolean.TRUE;
                    } else {
                        log.error(str);
                        return Boolean.FALSE;
                    }
                });
    }
}
