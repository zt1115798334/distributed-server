package com.example.distributedbookdetails.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.distributedbookdetails.entity.BookDetailsRest;
import com.example.distributedbookdetails.externalService.custom.SysConst;
import com.example.distributedbookdetails.externalService.domain.EsArticle;
import com.example.distributedbookdetails.externalService.domain.EsPage;
import com.example.distributedbookdetails.externalService.service.EsArticleService;
import com.example.distributedbookdetails.externalService.utils.EsParamsUtils;
import com.example.distributedbookdetails.service.BookDetailsRestService;
import com.example.distributedcommon.base.BaseResultMessage;
import com.example.distributedcommon.base.ResultMessage;
import com.example.distributedcommon.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2021/1/7 22:24
 * description:
 */
@AllArgsConstructor
@RequestMapping("api/bookDetailsRest")
@RestController
public class BookDetailsRestController extends BaseResultMessage {

    private final BookDetailsRestService bookDetailsRestService;

    private final EsArticleService esArticleService;

    private final String INDEX = "book_details_rest";


    @PostMapping("createIndex")
    public ResultMessage createIndex() throws Exception {
        bookDetailsRestService.createIndex(INDEX);
        return success();
    }

    @PostMapping("existIndex")
    public ResultMessage existIndex() throws Exception {
        boolean state = bookDetailsRestService.existIndex(INDEX);
        JSONObject result = new JSONObject();
        result.put("state", state);
        return success(result);
    }

    @PostMapping("deleteIndex")
    public ResultMessage deleteIndex() throws Exception {
        bookDetailsRestService.deleteIndex(INDEX);
        return success();
    }

    @PostMapping("addBookDetails")
    public ResultMessage addBookDetails() throws Exception {
        JSONObject paramJo = new JSONObject();
        DateUtils.DateRange dateTimeRange = DateUtils.findDateTimeRange(SysConst.TimeType.MONTH.getType());
        int pageNumber = 1;
        int pageSize = 1;
        paramJo.putAll(EsParamsUtils.getQueryExternalParams(
                SysConst.SOURCE_ALL,
                SysConst.SOURCE_ALL_LIST,
                dateTimeRange.getStartDateTimeStr(),
                dateTimeRange.getEndDateTimeStr(),
                SysConst.SearchArea.ALL.getType(),
                "北京",
                Collections.singletonList("tradition"),
                SysConst.ReadState.ALL.getCode(), false));
        EsPage<EsArticle> articleEsPage = esArticleService.findAllDataEsArticlePage(paramJo,
                pageNumber, pageSize, 1L,
                true);
        List<BookDetailsRest> bookDetailsRests = changeArticle(articleEsPage.getList());
        bookDetailsRestService.addBookDetails(INDEX, bookDetailsRests.get(0));
        return success();
    }

    @PostMapping("isExistsBookDetailsRest")
    public ResultMessage isExistsBookDetailsRest(@RequestParam String id) throws Exception {
        boolean state = bookDetailsRestService.isExistsBookDetailsRest(INDEX, id);
        JSONObject result = new JSONObject();
        result.put("state", state);
        return success(result);
    }

    @PostMapping("getBookDetailsRest")
    public ResultMessage getBookDetailsRest(@RequestParam String id) throws Exception {
        BookDetailsRest bookDetailsRest = bookDetailsRestService.getBookDetailsRest(INDEX, id);
        return success(bookDetailsRest);
    }

    @PostMapping("updateBookDetailsRest")
    public ResultMessage updateBookDetailsRest(@RequestParam String id) throws Exception {
        BookDetailsRest bookDetailsRest = bookDetailsRestService.getBookDetailsRest(INDEX, id);
        bookDetailsRest.setCleanTitle(bookDetailsRest.getCleanTitle() + "ddddd");
        boolean state = bookDetailsRestService.updateBookDetailsRest(INDEX, id, bookDetailsRest);
        JSONObject result = new JSONObject();
        result.put("state", state);
        return success(result);
    }

    @PostMapping("deleteBookDetailsRest")
    public ResultMessage deleteBookDetailsRest(@RequestParam String id) throws Exception {
        boolean state =  bookDetailsRestService.deleteBookDetailsRest(INDEX, id);
        JSONObject result = new JSONObject();
        result.put("state", state);
        return success(result);
    }

    @PostMapping("bulkBookDetailsRest")
    public ResultMessage bulkBookDetailsRest() throws Exception {
        JSONObject paramJo = new JSONObject();
        DateUtils.DateRange dateTimeRange = DateUtils.findDateTimeRange(SysConst.TimeType.MONTH.getType());
        int pageNumber = 1;
        int pageSize = 100;
        paramJo.putAll(EsParamsUtils.getQueryExternalParams(
                SysConst.SOURCE_ALL,
                SysConst.SOURCE_ALL_LIST,
                dateTimeRange.getStartDateTimeStr(),
                dateTimeRange.getEndDateTimeStr(),
                SysConst.SearchArea.ALL.getType(),
                "北京",
                Collections.singletonList("tradition"),
                SysConst.ReadState.ALL.getCode(), false));
        EsPage<EsArticle> articleEsPage = esArticleService.findAllDataEsArticlePage(paramJo,
                pageNumber, pageSize, 1L,
                true);
        List<BookDetailsRest> bookDetailsRests = changeArticle(articleEsPage.getList());
        boolean b = bookDetailsRestService.bulkBookDetailsRest(INDEX, bookDetailsRests);
        System.out.println("b = " + b);
        Long totalElements = articleEsPage.getTotalElements();
        System.out.println("totalElements = " + totalElements);
        for (int i = 2; i < articleEsPage.getTotalElements() / 10; i++) {
            articleEsPage = esArticleService.findAllDataEsArticlePage(paramJo,
                    i++, pageSize, 1L,
                    true);
            bookDetailsRests = changeArticle(articleEsPage.getList());
            bookDetailsRestService.bulkBookDetailsRest(INDEX, bookDetailsRests);
        }
        return success();
    }

    @PostMapping("searchRequest")
    public ResultMessage searchRequest(@RequestParam String keyword) throws Exception {
        List<Map<String, Object>> maps = bookDetailsRestService.searchRequest(INDEX, keyword);
        return success(maps);
    }

    @PostMapping("searchAllRequest")
    public ResultMessage searchAllRequest() throws Exception {
        List<String> strings = bookDetailsRestService.searchAllRequest(INDEX);
        return success(strings);
    }

    private List<BookDetailsRest> changeArticle(List<EsArticle> esArticles) {
        return esArticles.parallelStream()
                .map(esArticle -> new BookDetailsRest(esArticle.getId(), esArticle.getSiteName(),
                        esArticle.getAuthor(), esArticle.getTitle(),
                        esArticle.getCleanTitle(), esArticle.getSummary(), esArticle.getContent(),
                        DateUtils.localDateTimeToDate(esArticle.getPublishTime())))
                .collect(Collectors.toList());
    }

}
