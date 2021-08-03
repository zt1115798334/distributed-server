package com.example.distributedbookdetails.controller;

import com.example.distributedbookdetails.entity.BookDetails;
import com.example.distributedbookdetails.service.BookDetailsService;
import com.example.distributedcommon.base.BaseResultMessage;
import com.example.distributedcommon.base.ResultMessage;
import com.example.distributedcommon.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("init")
    public ResultMessage init() {

        return success();
    }

    @PostMapping("searchBook")
    public ResultMessage searchBook(@RequestParam(defaultValue = "") String keyword,
                                    @RequestParam(defaultValue = "1") Integer pageNumber,
                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<BookDetails> detailsPage = bookDetailsService.searchBook(pageNumber, pageSize, keyword);
        return success(pageNumber, pageSize, detailsPage.getTotalElements(), detailsPage.toList());
    }

}
