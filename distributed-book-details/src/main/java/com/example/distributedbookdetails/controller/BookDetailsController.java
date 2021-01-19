package com.example.distributedbookdetails.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.distributedbookdetails.entity.BookDetails;
import com.example.distributedbookdetails.externalService.domain.EsArticle;
import com.example.distributedbookdetails.externalService.domain.EsPage;
import com.example.distributedbookdetails.externalService.service.EsArticleService;
import com.example.distributedbookdetails.externalService.utils.EsParamsUtils;
import com.example.distributedbookdetails.service.BookDetailsService;
import com.example.distributedcommon.base.BaseResultMessage;
import com.example.distributedcommon.base.ResultMessage;
import com.example.distributedcommon.custom.SysConst;
import com.example.distributedcommon.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2021/1/7 22:24
 * description:
 */
@AllArgsConstructor
@RequestMapping("api/bookDetails")
@RestController
public class BookDetailsController extends BaseResultMessage {

    private final BookDetailsService bookDetailsService;

    private final EsArticleService esArticleService;

    @PostMapping("init")
    public ResultMessage init() {

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
        saveArticle(articleEsPage.getList());
        Long totalElements = articleEsPage.getTotalElements();
        System.out.println("totalElements = " + totalElements);
        for (int i = 2; i < articleEsPage.getTotalElements() / 10; i++) {
            articleEsPage = esArticleService.findAllDataEsArticlePage(paramJo,
                    i++, pageSize, 1L,
                    true);
            saveArticle(articleEsPage.getList());
        }
        return success();
    }


    private void saveArticle(List<EsArticle> esArticles) {
        List<BookDetails> bookDetails = esArticles.parallelStream()
                .map(esArticle -> new BookDetails(esArticle.getId(), esArticle.getSiteName(),
                        esArticle.getAuthor(), esArticle.getTitle(),
                        esArticle.getCleanTitle(), esArticle.getSummary(), esArticle.getContent(),
                        DateUtils.localDateTimeToDate(esArticle.getPublishTime())))
                .collect(Collectors.toList());
        bookDetailsService.saveAll(bookDetails);

    }
    @PostMapping("searchBook")
    public ResultMessage searchBook(@RequestParam(defaultValue = "") String keyword,
                                    @RequestParam(defaultValue = "1") Integer pageNumber,
                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<BookDetails> detailsPage = bookDetailsService.searchBook(pageNumber, pageSize, keyword);
        return success(pageNumber, pageSize, detailsPage.getTotalElements(), detailsPage.toList());
    }

}
