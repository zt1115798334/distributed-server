package com.example.distributedbookdetails.service;

import com.example.distributedbookdetails.entity.BookDetails;
import com.example.distributedbookdetails.entity.BookDetailsRest;
import org.elasticsearch.client.security.user.User;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2021/1/7 21:27
 * description:
 */
public interface BookDetailsRestService {

    /**
     * 创建索引
     *
     * @param index 索引
     */
    boolean createIndex(String index) throws Exception;

    /**
     * 判断索引是否存在
     *
     * @param index 索引
     */
    boolean existIndex(String index) throws Exception;

    /**
     * 删除索引
     *
     * @param index 索引
     */
    boolean deleteIndex(String index) throws Exception;

    /**
     * 新增文档
     *
     * @param index   索引
     * @param bookDetailsRest      bookDetailsRest
     */
    boolean addBookDetails(String index, BookDetailsRest bookDetailsRest) throws Exception;

    /**
     * 判断是否存在文档
     *
     * @param index 索引
     * @param id id
     */
    boolean isExistsBookDetailsRest(String index, String id) throws Exception;

    /**
     * 获取文档
     *
     * @param index 索引
     * @param id id
     */
    BookDetailsRest getBookDetailsRest(String index, String id) throws Exception;

    /**
     * 更新文档
     *
     * @param index 索引
     * @param id id
     * @param bookDetailsRest bookDetailsRest
     */
    boolean updateBookDetailsRest(String index, String id, BookDetailsRest bookDetailsRest) throws Exception;

    /**
     * 删除文档
     *
     * @param index 索引
     * @param id id
     */
    boolean deleteBookDetailsRest(String index, String id) throws Exception;

    /**
     * 批量插入
     *
     * @param index 索引
     * @param bookDetailsList 文章集合
     */
    boolean bulkBookDetailsRest(String index, List<BookDetailsRest> bookDetailsList) throws Exception;

    /**
     * 搜索请求
     *
     * @param index 索引
     * @param keyword keyword
     */
    List<Map<String, Object>> searchRequest(String index, String keyword) throws Exception;

    /**
     * 搜索所有id
     *
     * @param index index
     */
    List<String> searchAllRequest(String index) throws Exception;
}
