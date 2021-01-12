package com.example.distributedbookdetails;

import com.example.distributedbookdetails.service.BookDetailsRestService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class DistributedBookDetailsApplicationTests {

    @Autowired
    private BookDetailsRestService bookDetailsRestService;

    @Test
    void createIndexTest() throws Exception {
        // 创建索引
        bookDetailsRestService.createIndex("test_index");
    }

    @Test
    void existIndexTest() throws Exception {
        // 判断索引是否存在
        bookDetailsRestService.existIndex("test_index");
    }

    @Test
    void deleteIndexTest() throws Exception {
        // 删除索引
        bookDetailsRestService.deleteIndex("test_index");
    }

    @Test
    void addDocumentTest() throws Exception {
        // 新增文档
//        User user = new User();
//        user.setId(1L);
//        user.setAge(12);
//        user.setName("测试name");
//        user.setDescription("测试des");
//
//        bookDetailsRestService.addDocument("test_index", user.getId().toString(), JsonUtils.objectToJson(user));
    }

    @Test
    void isExistsDocumentTest() throws Exception {
        // 判断是否存在文档
        bookDetailsRestService.isExistsDocument("test_index", "1");
    }

    @Test
    void getDocumentTest() throws Exception {
        // 获取文档
        bookDetailsRestService.getDocument("test_index", "1");

    }

    @Test
    void updateDocumentTest() throws Exception {
        // 更新文档
//        User user = new User();
//        user.setId(1L);
//        user.setAge(33);
//        user.setName("测试name");
//        user.setDescription("测试des");
//
//        bookDetailsRestService.updateDocument("test_index", user.getId().toString(), JsonUtils.objectToJson(user));
    }

    @Test
    void deleteDocumentTest() throws Exception {
        // 删除文档
        bookDetailsRestService.deleteDocument("test_index", "1");
    }

    @Test
    void bulkRequestTest() throws Exception {
//        // 批量插入
//        List<User> users = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            User user = new User();
//            user.setId((long) i);
//            user.setAge(i);
//            user.setName("测试name" + i);
//            user.setDescription("测试des" + i);
//            users.add(user);
//        }
//
//        bookDetailsRestService.bulkRequest("test_index", users);
    }

    @Test
    void searchRequestTest() throws Exception {
        // 搜索请求
        bookDetailsRestService.searchRequest("test_index", "测试");
    }

    @Test
    void searchAllRequestTest() throws Exception {
        // 搜索请求
        bookDetailsRestService.searchAllRequest("test_index");
    }

    @BeforeEach
    void testBefore() {
//        log.info("测试开始!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @AfterEach
    void testAfter() {
//        log.info("测试结束!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

}
