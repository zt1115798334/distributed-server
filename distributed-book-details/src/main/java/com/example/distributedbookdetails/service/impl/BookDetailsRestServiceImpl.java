package com.example.distributedbookdetails.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.distributedbookdetails.entity.BookDetailsRest;
import com.example.distributedbookdetails.service.BookDetailsRestService;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.ResourceUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2021/1/7 21:28
 * description:
 */
@Slf4j
@AllArgsConstructor
@Service
public class BookDetailsRestServiceImpl implements BookDetailsRestService {


    private final RestHighLevelClient restHighLevelClient;

    @Override
    public boolean createIndex(String index) throws Exception {
        // 判断索引是否存在
        if (this.existIndex(index)) {
            return true;
        }
        // 创建索引
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
        createIndexRequest.settings(Settings.builder().put("number_of_shards",5).
                put("number_of_replicas",1));
        createIndexRequest.mapping(ResourceUtil.readFileFromClasspath("bookDetails.json"),XContentType.JSON);
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        return createIndexResponse.isAcknowledged();
    }


    @Override
    public boolean existIndex(String index) throws Exception {
        // 判断索引是否存在
        GetIndexRequest getIndexRequest = new GetIndexRequest(index);
        return restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
    }


    @Override
    public boolean deleteIndex(String index) throws Exception {
        // 判断索引是否存在
        if (!this.existIndex(index)) {
            return true;
        }
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        return acknowledgedResponse.isAcknowledged();
    }


    @Override
    public boolean addBookDetails(String index, BookDetailsRest bookDetailsRest) throws Exception {
        if (!this.createIndex(index)) {
            return false;
        }

        IndexRequest indexRequest = new IndexRequest(index);
        // 设置超时时间
        indexRequest.id(bookDetailsRest.getId());
        indexRequest.timeout(TimeValue.timeValueSeconds(1));
        // 转换为json字符串
        System.out.println(JSONObject.toJSONString(bookDetailsRest));
        indexRequest.source(JSONObject.toJSONString(bookDetailsRest), XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        return indexResponse.status().getStatus() == 200;
    }


    @Override
    public boolean isExistsBookDetailsRest(String index, String id) throws Exception {
        // 判断是否存在文档
        GetRequest getRequest = new GetRequest(index, id);
        // 不获取返回的_source的上下文
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        return restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
    }


    @Override
    public BookDetailsRest getBookDetailsRest(String index, String id) throws Exception {
        // 获取文档
        GetRequest getRequest = new GetRequest(index, id);
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        String sourceAsString = getResponse.getSourceAsString();
        return JSONObject.toJavaObject(JSONObject.parseObject(sourceAsString), BookDetailsRest.class);
    }


    @Override
    public boolean updateBookDetailsRest(String index, String id, BookDetailsRest bookDetailsRest) throws Exception {
        // 更新文档
        UpdateRequest updateRequest = new UpdateRequest(index, id);
        updateRequest.timeout(TimeValue.timeValueSeconds(1));
        updateRequest.doc(JSONObject.toJSONString(bookDetailsRest), XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        return updateResponse.status().getStatus() == 200;
    }


    @Override
    public boolean deleteBookDetailsRest(String index, String id) throws Exception {
        if (!this.isExistsBookDetailsRest(index, id)) {
            return true;
        }

        // 删除文档
        DeleteRequest deleteRequest = new DeleteRequest(index, id);
        deleteRequest.timeout(TimeValue.timeValueSeconds(1));
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        return deleteResponse.status().getStatus() == 200;
    }


    @Override
    public boolean bulkBookDetailsRest(String index, List<BookDetailsRest> bookDetailsList) throws Exception {
        // 批量插入
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout(TimeValue.timeValueSeconds(1));
        bookDetailsList.forEach(x -> bulkRequest.add(
                new IndexRequest(index)
                        .id(x.getId())
                        .source(JSONObject.toJSONString(x), XContentType.JSON)));
        BulkResponse bulkItemResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulkItemResponse.hasFailures();
    }


    @Override
    public List<Map<String, Object>> searchRequest(String index, String keyword) throws Exception {
        // 搜索请求
        SearchRequest searchRequest;
        if (StringUtils.isEmpty(index)) {
            searchRequest = new SearchRequest();
        } else {
            searchRequest = new SearchRequest(index);
        }
        // 条件构造
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 第几页
        searchSourceBuilder.from(0);
        // 每页多少条数据
        searchSourceBuilder.size(1000);
        // 配置高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("name").field("description");
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);
        // 精确查询
//        QueryBuilders.termQuery();
        // 匹配所有
//        QueryBuilders.matchAllQuery();
        // 最细粒度划分：ik_max_word，最粗粒度划分：ik_smart
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(keyword, "content", "cleanTitle").analyzer("ik_max_word"));
//        searchSourceBuilder.query(QueryBuilders.matchQuery("content", keyWord));
        searchSourceBuilder.timeout(TimeValue.timeValueSeconds(10));

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        List<Map<String, Object>> results = new ArrayList<>();
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            Map<String, HighlightField> highlightFieldMap = searchHit.getHighlightFields();
            HighlightField content = highlightFieldMap.get("content");
            HighlightField cleanTitle = highlightFieldMap.get("cleanTitle");
            // 原来的结果
            Map<String, Object> sourceMap = searchHit.getSourceAsMap();
            // 解析高亮字段，替换掉原来的字段
            if (content != null) {
                Text[] fragments = content.getFragments();
                StringBuilder n_title = new StringBuilder();
                for (Text text : fragments) {
                    n_title.append(text);
                }
                sourceMap.put("content", n_title.toString());
            }
            if (cleanTitle != null) {
                Text[] fragments = cleanTitle.getFragments();
                StringBuilder n_description = new StringBuilder();
                for (Text text : fragments) {
                    n_description.append(text);
                }
                sourceMap.put("cleanTitle", n_description.toString());
            }
            results.add(sourceMap);
        }
        return results;
    }


    @Override
    public List<String> searchAllRequest(String index) throws Exception {
        // 搜索请求
        SearchRequest searchRequest;
        if (StringUtils.isEmpty(index)) {
            searchRequest = new SearchRequest();
        } else {
            searchRequest = new SearchRequest(index);
        }
        // 条件构造
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 第几页
        searchSourceBuilder.from(0);
        // 每页多少条数据
        searchSourceBuilder.size(1000);
        // 匹配所有
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.timeout(TimeValue.timeValueSeconds(10));

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        List<String> results = Lists.newArrayList();

        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            results.add(searchHit.getId());
        }
        return results;
    }
}
