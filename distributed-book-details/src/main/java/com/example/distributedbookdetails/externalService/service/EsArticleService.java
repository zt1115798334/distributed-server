package com.example.distributedbookdetails.externalService.service;

import com.alibaba.fastjson.JSONObject;
import com.example.distributedbookdetails.externalService.base.ConstantService;
import com.example.distributedbookdetails.externalService.domain.EsArticle;
import com.example.distributedbookdetails.externalService.domain.EsPage;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2018/8/20 11:24
 * description: es文章查询的业务层
 */
public interface EsArticleService extends ConstantService {
    /**
     * 获取所有数据文章列表
     *
     * @param params              其他参数
     * @param pageNumber          页数
     * @param pageSize            每页条数
     * @param containContentState 是否包含文章内容
     * @return EsPage
     */
    EsPage<EsArticle> findAllDataEsArticlePage(JSONObject params, int pageNumber, int pageSize, Long userId,
                                               Boolean containContentState);
}
