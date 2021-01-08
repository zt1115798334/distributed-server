package com.example.distributedbookdetails.externalService.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.example.distributedbookdetails.externalService.domain.EsArticle;
import com.example.distributedbookdetails.externalService.domain.EsPage;
import com.example.distributedbookdetails.externalService.service.EsArticleService;
import com.example.distributedbookdetails.externalService.service.EsInterfaceService;
import com.example.distributedbookdetails.externalService.utils.ArticleUtils;
import com.example.distributedbookdetails.externalService.utils.EsParamsUtils;
import com.example.distributedbookdetails.externalService.utils.EsUtils;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2018/8/20 11:31
 * description:
 */
@AllArgsConstructor
@Slf4j
@Service
public class EsArticleServiceImpl implements EsArticleService {

    private final EsInterfaceService esInterfaceService;

    private Optional<EsArticle> jsonObjToArticle(String str) {
        return Optional.ofNullable(str).flatMap(stt -> EsUtils.getJSONArrayEsResult(stt)
                .filter(jsonArray -> jsonArray.size() > 0)
                .map(jsonArray -> jsonArray.getJSONObject(0))
                .map(ArticleUtils::jsonObjectConvertEsArticle));

    }

    private List<EsArticle> jsonToArticleList(String str) {
        Optional<JSONArray> jsonArrayOptional = EsUtils.getJSONArrayEsResult(str);
        return jsonArrayOptional.map(json -> json.stream()
                .map(obj -> TypeUtils.castToJavaBean(obj, JSONObject.class))
                .map(ArticleUtils::jsonObjectConvertEsArticle)
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList()))
                .orElseGet(Collections::emptyList);
    }

    private EsPage<EsArticle> jsonToArticlePage(String str) {
        List<EsArticle> esArticleList = Lists.newArrayList();
        long count = 0L;
        Optional<JSONObject> objectOptional = Optional.ofNullable(str).map(JSON::parseObject);
        if (objectOptional.isPresent()) {
            JSONObject jo = objectOptional.get();
            if (Objects.equal("0", jo.getString("code"))) {
                count = jo.getLongValue("count");
                esArticleList = jsonToArticleList(str);
            } else {
                log.error("es取数异常，信息提示为：{}", str);
            }
        }
        return new EsPage<>(esArticleList, count);
    }
    @Override
    public EsPage<EsArticle> findAllDataEsArticlePage(JSONObject params, int pageNumber, int pageSize, Long userId,
                                                      Boolean containContentState) {
        params.putAll(EsParamsUtils.getQueryUserIdParams(userId));//添加userId
        String str = esInterfaceService.fullQuery(params, pageNumber, pageSize, containContentState, Boolean.TRUE);
        return jsonToArticlePage(str);
    }
}
