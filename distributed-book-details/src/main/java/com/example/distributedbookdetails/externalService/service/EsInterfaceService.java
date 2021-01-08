package com.example.distributedbookdetails.externalService.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2018/8/22 10:55
 * description: es接口业务层
 */
public interface EsInterfaceService extends InterfaceBaseService {
    /**
     * 全文检索接口  分页
     *
     * @param params              参数
     * @param pageNumber          页数
     * @param pageSize            每页显示数量
     * @param containContentState 是否包含文章内容
     * @param queryParamsExist    queryParams集合size如果为空,true>查询数据,false>不查询数据
     * @return String
     */
    String fullQuery(JSONObject params, int pageNumber, int pageSize,
                     Boolean containContentState, Boolean queryParamsExist);


}
