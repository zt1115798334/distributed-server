package com.example.distributedbookdetails.externalService.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.distributedbookdetails.externalService.functional.QueryParamsExistFunction;
import com.example.distributedbookdetails.externalService.service.EsInterfaceService;
import com.example.distributedbookdetails.externalService.url.EsUrlConstants;
import com.example.distributedbookdetails.externalService.utils.EsUtils;
import com.example.distributedbookdetails.properties.CustomProperties;
import com.example.distributedcommon.utils.HttpClientUtils;
import com.example.distributedcommon.utils.MD5Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2018/8/22 10:55
 * description:
 */
@AllArgsConstructor
@Service
public class EsInterfaceServiceImpl implements EsInterfaceService {

    private final CustomProperties customProperties;

    public StringBuffer splicingUrl(String url) {
        CustomProperties.Es esProperties = customProperties.getEs();
        String key = esProperties.getKey();
        String host = esProperties.getHost();
        String appId = esProperties.getAppId();
        long time = System.currentTimeMillis() / 1000;// 秒
        String token = MD5Utils.generateToken(key, time);
        StringBuffer restUrl = new StringBuffer();
        restUrl.append(host).append(url).append("?call_id=").append(time).append("&token=").append(token).append("&appid=").append(appId);
        return restUrl;
    }

    public StringBuffer splicingUrl(String url, int pageNumber, int pageSize) {
        return splicingUrl(url).append("&page_no=").append(pageNumber).append("&page_size=").append(pageSize);
    }


    @Override
    public String fullQuery(JSONObject params, int pageNumber, int pageSize,
                            Boolean containContentState, Boolean queryParamsExist) {
        QueryParamsExistFunction function = () -> {
            String fullUrl = containContentState ? EsUrlConstants.URL_FULL_QUERY_WITH_CONTENT : EsUrlConstants.URL_FULL_QUERY_WITH_CONTENT;
            String url = splicingUrl(fullUrl, pageNumber, pageSize).toString();
            return HttpClientUtils.getInstance().httpPostJson(url, params.getInnerMap());
        };
        return execute(params, queryParamsExist, function, EsUtils.getEsArraysEmpty);
    }

    private String execute(JSONObject params, Boolean queryParamsExist, QueryParamsExistFunction function, String defaultVal) {
        if (queryParamsExist || (params.containsKey("queryParams") && params.getJSONArray("queryParams").size() != 0)) {
            return function.execute();
        }
        return defaultVal;
    }

}
